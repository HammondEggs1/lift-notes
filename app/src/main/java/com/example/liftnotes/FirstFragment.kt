package com.example.liftnotes

import DayAdapter
import ExerciseAdapter
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.marker.Marker
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Collections
import java.util.Random


/**
 * A simple [Fragment] subclass as the default destination in the navigation.*/

class FirstFragment : Fragment(), DayAdapter.OnItemClickListener, ExerciseAdapter.OnItemClickListenerEx {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DayAdapter
    private lateinit var context: Context

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

        val data = listOf("Leg Day", "Push Day", "Pull Day")
        adapter = DayAdapter(data, this)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
        lateinit var listdata :MutableList<List<FloatEntry>>
        for (i in 1..Random().nextInt(10)) {
            listdata.add(entriesOf(Random().nextDouble()*100, Random().nextDouble()*100, Random().nextDouble()*100, Random().nextDouble()*100))
        }
        val list = listdata.toList()
        val chartEntryModel = entryModelOf(list)// this does not work for somereason although
        binding.chartView.setModel(chartEntryModel)//entryModelOf(entriesOf(4f, 12f, 8f, 16f), entriesOf(12f, 16f, 4f, 12f))
    }                                              // works and both are  a list<list<floatEntry>

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClick(position: Int, day: String) {
        // Handle the click here
        //SecondFragment.newInstance(day)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onItemClickEx(position: Int, exercise: String) {
        ExerciseView.newInstanceEx(exercise)
        findNavController().navigate(R.id.action_FirstFragment_to_ExerciseView)

    }

}