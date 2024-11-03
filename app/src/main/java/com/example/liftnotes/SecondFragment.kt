package com.example.liftnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentSecondBinding
import com.google.gson.Gson
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainActivity.SharedViewModel by activityViewModels()

    //Data Store created
    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "workouts"
    )

    // List to store workout data
    private var workoutList = mutableListOf<LiftInfo>()

    val workout_key = stringPreferencesKey("workout_list")

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

        // Back button logic
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        lifecycleScope.launch {
            workoutList.addAll(loadworkoutlist())
        }

        // Add Workouts Button
        binding.addWorkoutsButton.setOnClickListener {
            val input = binding.inputWorkouts.text.toString()
            addMultipleWorkouts(input)
            lifecycleScope.launch {
                saveworkoutlist(workoutList)
            }
        }

        // Search Workouts Button
        binding.searchButton.setOnClickListener {
            val searchName = binding.searchWorkout.text.toString()
            val result = findMostRecentWorkout(searchName)
            if (result != null) {
                binding.displayResults.text = result.toString()
            } else {
                binding.displayResults.text = "Workout not found."
            }
        }
    }

    // Add multiple workouts from the input
    private fun addMultipleWorkouts(input: String) {
        val workouts = input.split(",")
        for (workout in workouts) {
            val trimmedWorkout = workout.trim()

            // Regex to match workout format: liftName sets(weight for reps)
            val regex = "(\\w+)\\s(\\d+)\\((\\d+)\\sfor\\s(\\d+)\\)".toRegex()
            val matchResult = regex.find(trimmedWorkout)

            if (matchResult != null) {
                val (liftName, sets, weight, reps) = matchResult.destructured
                workoutList.add(LiftInfo(liftName, sets.toInt(), weight.toInt(), reps.toInt()))
            } else {
                binding.displayResults.text = "Invalid format for: $workout"
            }
        }
        binding.displayResults.text = "Workouts added successfully."
    }

    // Find the most recent workout by name
    private fun findMostRecentWorkout(liftName: String): LiftInfo? {
        return workoutList.reversed().find { it.liftName.equals(liftName, ignoreCase = true) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Data class to store workout information
    data class LiftInfo(val liftName: String, val sets: Int, val weight: Int, val reps: Int) {
        override fun toString(): String {
            return "$liftName: $sets sets ($weight lbs for $reps reps)"
        }
    }

    //save workout list
    private suspend fun saveworkoutlist(workoutList: List<LiftInfo>) {
        val gson = Gson()
        val workoutJson = gson.toJson(workoutList)
        requireContext().userPreferencesDataStore.edit { preferences ->
            preferences[workout_key] = workoutJson
        }
    }

    private suspend fun loadworkoutlist(): List<LiftInfo> {
        val gson = Gson()
        val preferences = requireContext().userPreferencesDataStore.data.first()
        val workoutJson = preferences[workout_key] ?: return emptyList()
        //workoutList = gson.fromJson(workoutJson, Array<LiftInfo>::class.java).toMutableList()
        return gson.fromJson(workoutJson, Array<LiftInfo>::class.java).toList()
    }

}