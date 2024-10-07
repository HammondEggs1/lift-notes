package com.example.liftnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    // List to store workout data
    private val workoutList = mutableListOf<LiftInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button logic
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        // Add Workouts Button
        binding.addWorkoutsButton.setOnClickListener {
            val input = binding.inputWorkouts.text.toString()
            addMultipleWorkouts(input)
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
}
