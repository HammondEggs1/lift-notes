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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.liftnotes.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.launch
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Calendar
import java.util.Collections
import java.util.Random


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

        val data = mutableListOf("Leg Day", "Push Day", "Pull Day", "New Day")
        adapter = DayAdapter(data, this)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        var calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
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
        calendar.set(Calendar.DAY_OF_MONTH, 5);
        val d5 = calendar.time
        val graph = binding.graph
        lifecycleScope.launch {
            val context = requireContext()
            val exercises = ManageStorage.loadWorkoutList(context)
            for (exercise in exercises) {
                val rnd: Random = Random()
                val datalist = arrayOf( // need to turn whatever data into a list of times Date! and doubles
                    DataPoint(d1, rnd.nextDouble()*100),// data point can use date for x param
                    DataPoint(d2, rnd.nextDouble()*100),//see documentation for that
                    DataPoint(d3, rnd.nextDouble()*100),
                    DataPoint(d4, rnd.nextDouble()*100),
                    DataPoint(d5, rnd.nextDouble()*100)
                )
                val series = LineGraphSeries(datalist)
                series.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                series.title = exercise.liftName;
                graph.addSeries(series)
            }
            graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE;
            graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH
            graph.gridLabelRenderer.gridColor = Color.WHITE
            graph.gridLabelRenderer.setLabelFormatter(DateAsXAxisLabelFormatter(activity))
            graph.viewport.setXAxisBoundsManual(true);
            graph.setBackgroundColor(Color.parseColor("#00008B"))
            graph.gridLabelRenderer.setHumanRounding(false);
            graph.legendRenderer.isVisible = true;
            graph.legendRenderer.backgroundColor = 0
            graph.legendRenderer.textColor=Color.WHITE
            graph.legendRenderer.align = LegendRenderer.LegendAlign.BOTTOM;
            graph.gridLabelRenderer.numHorizontalLabels = 3
        }

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