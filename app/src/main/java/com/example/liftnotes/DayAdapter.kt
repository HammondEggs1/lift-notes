import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.FirstFragment
import com.example.liftnotes.R


class DayAdapter(private val data: List<String>, private val click: ExerciseAdapter.OnItemClickListenerEx) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    public lateinit var adapter: ExerciseAdapter
    private var listener: OnItemClickListener? = null
    val exercises = arrayOf(
        listOf("Back Squat", "Calf Pulses", "Leg Extensions", "Lunges"),
        listOf("Bench Press", "Skull Crushers", "Overhead Presses", "Face Pulls"),
        listOf("Bicep Curls", "Lat Pull Downs", "Deadlift", "Supermans", "Rows")
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.day_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
        recyclerView = holder.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)


        if(data[position]=="New Day") {
            holder.linearLayout.setOrientation(LinearLayout.VERTICAL);
            holder.linearLayout.setGravity(Gravity.CENTER)
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
            holder.button.setOnClickListener {
                // future add day ability here
            }
        } else {
            holder.button.setOnClickListener {
                listener?.onItemClick(position, data[position])
            }
            val data = exercises[position]
            adapter = ExerciseAdapter(data)

            adapter.setOnItemClickListenerEx(click)
            recyclerView.adapter = adapter
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.day_text)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
        val button : Button = itemView.findViewById(R.id.button_one)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, day: String)
    }


}