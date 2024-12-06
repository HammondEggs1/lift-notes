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

        binding.exerciseText.text = exText

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val graph = binding.graph
        // Load exercises from ManageStorage
        lifecycleScope.launch {
            val context = requireContext()
            val exercises = ManageStorage.loadWorkoutList(context)
            displayExercises(exercises)
            var i = 0
            var maxy = 0.0
            val alreadyGraphed : MutableList<String> = ArrayList()
            for (exercise in exercises) {
                val dataListMutable : MutableList<DataPoint> = ArrayList()
                if (alreadyGraphed.contains(exercise.liftName)) {
                    continue
                }
                alreadyGraphed.add(exercise.liftName)
                for (exercise2 in exercises) {
                    if (exercise.liftName == exercise2.liftName) {
                        dataListMutable.add(DataPoint(i.toDouble(), exercise2.weight.toDouble()))
                        if (exercise2.weight > maxy) {
                            maxy = exercise2.weight.toDouble()
                        }
                        i += 1
                    }

                }
                val dataList = dataListMutable.toTypedArray()
                val series = LineGraphSeries(dataList)
                val rnd: Random = Random()
                series.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                series.title = exercise.liftName; //BenchPress 10(2 for 200)
                graph.addSeries(series)
                i = 0
            }
            graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
            graph.gridLabelRenderer.gridColor = Color.WHITE
            //graph.gridLabelRenderer.setLabelFormatter(DateAsXAxisLabelFormatter(activity)) // cant use dates due to no
            //graph.viewport.setXAxisBoundsManual(true);                                     //dates in the data structure
            graph.setBackgroundColor(Color.parseColor("#00008B"))
            //graph.gridLabelRenderer.setHumanRounding(false) // causes bad y axis bug sometimes
            graph.viewport.setMinY(0.0) // can be removed if unwanted
            graph.viewport.setMaxY(maxy)
            graph.viewport.setYAxisBoundsManual(true);
            graph.legendRenderer.isVisible = true;
            graph.legendRenderer.backgroundColor = 0
            graph.legendRenderer.textColor=Color.WHITE
            graph.legendRenderer.align = LegendRenderer.LegendAlign.BOTTOM;
            graph.gridLabelRenderer.numHorizontalLabels = 3 // turn to zero to hide the x axis labels
            graph.gridLabelRenderer.numVerticalLabels = 3
        }
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_ExerciseView_to_FirstFragment)
        }
    }

    private fun displayExercises(exercises: List<ManageStorage.LiftInfo>) {
        // Format exercises for display
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
        private var exText = ""
        fun newInstanceEx(itemText: String) {
            exText = itemText
        }
    }
}
