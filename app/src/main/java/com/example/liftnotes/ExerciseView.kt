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

        // Load exercises from ManageStorage
        lifecycleScope.launch {
            val context = requireContext()
            val exercises = ManageStorage.loadWorkoutList(context)
            displayExercises(exercises)
        }
        var calendar: Calendar = Calendar.getInstance()//can be deleted just for show
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_WEEK - 3);
        calendar.set(Calendar.YEAR, 2024);
        val d1 = calendar.time
        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        val d2 = calendar.time
        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        val d3 = calendar.time
        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        val d4 = calendar.time
        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 5); //  can be deleted just for show
        val d5 = calendar.time
        val graph = binding.graph
        val rnd: Random = Random()
        val datalist = arrayOf(
            DataPoint(d1, rnd.nextDouble()*100),// data point can use date for x param
            DataPoint(d2, rnd.nextDouble()*100),
            DataPoint(d3, rnd.nextDouble()*100),
            DataPoint(d4, rnd.nextDouble()*100),
            DataPoint(d5, rnd.nextDouble()*100)
        )
        val series = LineGraphSeries(datalist)
        series.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        series.title = "Exercise";
        graph.addSeries(series)
        graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE
        graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE
        graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE
        graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
        graph.gridLabelRenderer.gridColor = Color.WHITE
        graph.gridLabelRenderer.setLabelFormatter(DateAsXAxisLabelFormatter(activity))
        graph.viewport.setXAxisBoundsManual(true);
        graph.setBackgroundColor(Color.parseColor("#00008B"))
        graph.gridLabelRenderer.setHumanRounding(false);
        graph.legendRenderer.isVisible = true
        graph.legendRenderer.backgroundColor = 0
        graph.legendRenderer.textColor=Color.WHITE
        graph.legendRenderer.align = LegendRenderer.LegendAlign.BOTTOM;
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
