package com.example.liftnotes;

import ExHistoryAdapter
import android.content.BroadcastReceiver
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.databinding.ExerciseViewBinding

class ExerciseView : Fragment() {
    private var _binding: ExerciseViewBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExHistoryAdapter

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

        binding.exerciseText.text = exText;

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = listOf("10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs")
        adapter = ExHistoryAdapter(data)
        recyclerView.adapter = adapter

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_ExerciseView_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var exText = "";
        fun newInstanceEx(itemText: String) {
            exText = itemText;
        }
    }
}
