package com.example.liftnotes

import DayAdapter
import ExerciseAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FirstFragment : Fragment(), DayAdapter.OnItemClickListener, ExerciseAdapter.OnItemClickListenerEx {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DayAdapter
    private lateinit var context: Context
    private val sharedViewModel: MainActivity.SharedViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val data = listOf("Leg Day", "Push Day", "Pull Day", "New Day")
        adapter = DayAdapter(data, this)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClick(position: Int, day: String) {
        // Handle the click here
        sharedViewModel.currentDay.value = day
        sharedViewModel.currentFragment.value = "second"
    }

    override fun onItemClickEx(position: Int, exercise: String) {
        sharedViewModel.currentExercise.value = exercise
        sharedViewModel.currentFragment.value = "exercise"

    }

}