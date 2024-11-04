package com.example.liftnotes;

import ExHistoryAdapter
import android.content.BroadcastReceiver
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.databinding.ExerciseViewBinding
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

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = listOf("10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs", "10/8/24 | 3x12 | 200 lbs")
        adapter = ExHistoryAdapter(data)
        recyclerView.adapter = adapter

        val calendar: Calendar = Calendar.getInstance()
        val graph = binding.graph
        for (i in 1..Random().nextInt(10)) {//wont be needed for this view, but if main view has a graph on it
            val rnd: Random = Random()
            val datalist = mutableListOf(( DataPoint(0.0,0.0)))
            for (j in 1..Random().nextInt(10)) {
                val d1: Date = calendar.time
                calendar.add(Calendar.DATE, 1)
                datalist.add(DataPoint(d1,Random().nextDouble()*100))
            }
            val series = LineGraphSeries(datalist.toTypedArray())
            series.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            series.title = "yippie$i";
            graph.addSeries(series)
        }
        graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE;
        graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE;
        graph.gridLabelRenderer.verticalLabelsColor = Color.WHITE;
        graph.gridLabelRenderer.horizontalLabelsColor = Color.WHITE;
        graph.gridLabelRenderer.gridStyle =GridLabelRenderer.GridStyle.BOTH
        graph.gridLabelRenderer.gridColor = Color.WHITE
        graph.gridLabelRenderer.setLabelFormatter(DateAsXAxisLabelFormatter(activity))
        graph.viewport.setXAxisBoundsManual(true);
        graph.setBackgroundColor(Color.parseColor("#00008B"))
        graph.gridLabelRenderer.setHumanRounding(false);
        graph.legendRenderer.isVisible = true;
        graph.legendRenderer.align = LegendRenderer.LegendAlign.BOTTOM;
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
