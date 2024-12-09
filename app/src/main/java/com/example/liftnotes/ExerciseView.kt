package com.example.liftnotes

import ExHistoryAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.databinding.ExerciseViewBinding
import kotlinx.coroutines.launch
import android.graphics.Color
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.Calendar
import java.util.Date
import java.util.Random

class ExerciseView : Fragment() {
    private var _binding: ExerciseViewBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExHistoryAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseText.text = exText

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val graph = binding.graph

        lifecycleScope.launch {
            val context = requireContext()
            val exercises = ManageStorage.loadWorkoutList(context).filter { it.liftName == exText }
            displayExercises(exercises)

            val alreadyGraphed: MutableSet<String> = mutableSetOf()
            var maxy = 0.0
            for (exercise in exercises) {
                if (alreadyGraphed.contains(exercise.liftName)) continue
                alreadyGraphed.add(exercise.liftName)

                val dataList = exercises.filter { it.liftName == exercise.liftName }
                    .mapIndexed { i, e -> DataPoint(i.toDouble(), e.weight.toDouble()) }
                    .toTypedArray()
                val series = LineGraphSeries(dataList)

                val rnd = Random()
                series.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                series.title = exercise.liftName
                graph.addSeries(series)

                maxy = maxOf(maxy, dataList.maxOf { it.y })
            }

            graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE
            graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE
            graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
            graph.gridLabelRenderer.gridColor = Color.WHITE
            graph.setBackgroundColor(Color.parseColor("#00008B"))
            graph.viewport.setMinY(0.0)
            graph.viewport.setMaxY(maxy)
            graph.viewport.isYAxisBoundsManual = true
            graph.legendRenderer.isVisible = true
            graph.legendRenderer.backgroundColor = 0
            graph.legendRenderer.textColor = Color.WHITE
            graph.legendRenderer.align = LegendRenderer.LegendAlign.BOTTOM
            graph.gridLabelRenderer.numHorizontalLabels = 3
            graph.gridLabelRenderer.numVerticalLabels = 3
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_ExerciseView_to_FirstFragment)
        }
    }

    private fun displayExercises(exercises: List<ManageStorage.LiftInfo>) {
        val formattedData = exercises.map {
            "${it.liftName} | ${it.sets}x${it.reps} | ${it.weight} lbs"
        }
        adapter = ExHistoryAdapter(formattedData)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var exText: String = ""

        fun newInstanceEx(itemText: String) {
            exText = itemText
        }
    }
}
