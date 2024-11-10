package com.example.liftnotes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.liftnotes.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainActivity.SharedViewModel by activityViewModels()

    private val TAG = "NotesFragment"

    // To store all workouts (using a Map where the key is the workout name)
    private val workoutMap = mutableMapOf<String, MutableList<ManageStorage.LiftInfo>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.currentDay.observe(viewLifecycleOwner) { data ->
            binding.dayName.text = data;
        }

        // Load saved workouts from SharedPreferences
        loadSavedWorkouts()

        // Button click to save multiple workouts
        binding.saveWorkoutButton.setOnClickListener {
            val input = binding.noteEditText.text.toString()
            if (input.isNotEmpty()) {
                addWorkoutsFromTextBox(input)  // Save each line as a separate workout
            }
        }

        // Button click to retrieve the most recent workout by name
        binding.retrieveWorkoutButton.setOnClickListener {
            val workoutName = getCurrentLineText(binding.noteEditText)
            if (workoutName.isNotEmpty()) {
                retrieveMostRecentWorkout(workoutName)
            } else {
                binding.noteEditText.setText("Please place the cursor on a workout line to search.")
            }
        }
    }

    // Retrieve only the current line text in EditText based on cursor position
    private fun getCurrentLineText(editText: EditText): String {
        val selectionStart = editText.selectionStart
        val text = editText.text.toString()
        val lines = text.lines()

        var cumulativeLength = 0
        for (line in lines) {
            cumulativeLength += line.length + 1 // +1 for the newline character
            if (selectionStart <= cumulativeLength) {
                return line.trim() // Return the line the cursor is currently on
            }
        }
        return ""
    }

    // Add workouts by parsing each line in the input
    private fun addWorkoutsFromTextBox(input: String) {
        val lines = input.lines()

        for (line in lines) {
            val trimmedWorkout = line.trim()
            val regex = "(.+?)\\s(\\d+)\\((\\d+)\\sfor\\s(\\d+)\\)".toRegex(RegexOption.IGNORE_CASE)
            val matchResult = regex.find(trimmedWorkout)

            if (matchResult != null) {
                val (liftName, sets, weight, reps) = matchResult.destructured
                val newWorkout = ManageStorage.LiftInfo(liftName.trim(), sets.toInt(), weight.toInt(), reps.toInt())

                if (workoutMap.containsKey(liftName)) {
                    workoutMap[liftName]?.add(newWorkout)
                } else {
                    workoutMap[liftName] = mutableListOf(newWorkout)
                }
                Log.d(TAG, "Workout added: $newWorkout")
            } else {
                Log.d(TAG, "Invalid format for workout: $trimmedWorkout")
            }
        }

        // Save all added workouts at once
        saveWorkouts()
    }

    private fun saveWorkouts() {
        // Flatten the map to a list for storage
        val allWorkouts = workoutMap.values.flatten()

        // Save the workouts to DataStore
        lifecycleScope.launch {
            ManageStorage.saveWorkoutList(requireContext(), allWorkouts)
            Log.d(TAG, "All workouts saved: $allWorkouts")
        }
    }

    private fun loadSavedWorkouts() {
        // Load workouts from DataStore
        lifecycleScope.launch {
            val loadedWorkouts = ManageStorage.loadWorkoutList(requireContext())
            workoutMap.clear()

            // Rebuild the workoutMap from the loaded workouts
            for (workout in loadedWorkouts) {
                if (workoutMap.containsKey(workout.liftName)) {
                    workoutMap[workout.liftName]?.add(workout)
                } else {
                    workoutMap[workout.liftName] = mutableListOf(workout)
                }
            }
            Log.d(TAG, "Loaded workouts: $workoutMap")
        }
    }

    private fun retrieveMostRecentWorkout(inputName: String) {
        val normalizedInputName = inputName.trim().lowercase()

        val workouts = workoutMap.entries.firstOrNull {
            it.key.lowercase() == normalizedInputName
        }?.value

        if (workouts != null && workouts.isNotEmpty()) {
            val mostRecentWorkout = workouts.last()
            val currentText = binding.noteEditText.text.toString()
            val newText = "$currentText${mostRecentWorkout.toString()}"

            binding.noteEditText.setText(newText)
            binding.noteEditText.setSelection(newText.length) // Move cursor to the end of the text
            Log.d(TAG, "Most recent workout retrieved: $mostRecentWorkout")
        } else {
            Log.d(TAG, "No workout found for $inputName")
            binding.noteEditText.append("\nNo workout found for $inputName")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}