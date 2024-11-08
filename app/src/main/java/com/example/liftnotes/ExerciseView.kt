package com.example.liftnotes;

import ExHistoryAdapter
import android.content.BroadcastReceiver
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.databinding.ExerciseViewBinding


class ExerciseView : Fragment() {
    private var _binding: ExerciseViewBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExHistoryAdapter
    private val sharedViewModel: MainActivity.SharedViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var receiver: BroadcastReceiver
    private lateinit var dayName: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = ExerciseViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.currentExercise.observe(viewLifecycleOwner) { data ->
            binding.exerciseText.text = data;
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = listOf("10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs")
        adapter = ExHistoryAdapter(data)
        recyclerView.adapter = adapter

        binding.back.setOnClickListener {
            sharedViewModel.currentFragment.value = "first"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
