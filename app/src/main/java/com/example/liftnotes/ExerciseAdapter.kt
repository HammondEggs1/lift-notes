import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.liftnotes.R

class ExerciseAdapter(private val data: List<String>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private var listener: OnItemClickListenerEx? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
        holder.button.setOnClickListener {
            listener?.onItemClickEx(position, data[position])
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.exercise_text)
        val button: androidx.appcompat.widget.AppCompatImageButton = itemView.findViewById(R.id.exercise_button2)
    }

    interface OnItemClickListenerEx {
        fun onItemClickEx(position: Int, exercise: String)
    }

    fun setOnItemClickListenerEx(listener: OnItemClickListenerEx) {
        this.listener = listener
    }
}