package campus.tech.kakao.map

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemHistoryBinding

class HistoryAdapter(private val onDelete: (String) -> Unit) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var cursor: Cursor? = null

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String) {
            binding.tvName.text = name
            binding.ibDelete.setOnClickListener {
                onDelete(name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor?.apply {
            moveToPosition(position)
            val name = getString(getColumnIndexOrThrow(HistoryContract.COLUMN_NAME))
            holder.bind(name)
        }
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    fun submitCursor(cursor: Cursor?) {
        this.cursor = cursor
        notifyDataSetChanged()
    }
}
