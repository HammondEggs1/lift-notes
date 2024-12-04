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
import com.example.liftnotes.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.launch
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

        val graph = binding.graph
        lifecycleScope.launch {
            val context = requireContext()
            val exercises = ManageStorage.loadWorkoutList(context) // need format of title = exercise and array of weight values
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
            graph.gridLabelRenderer.numHorizontalLabels = 3
            graph.gridLabelRenderer.numVerticalLabels = 3
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