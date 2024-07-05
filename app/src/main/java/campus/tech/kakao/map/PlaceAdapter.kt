package campus.tech.kakao.map

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemPlaceBinding

class PlaceAdapter(private val onClick: (String) -> Unit) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var cursor: Cursor? = null

    inner class ViewHolder(private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, address: String, category: String) {
            binding.tvName.text = name
            binding.tvAddress.text = address
            binding.tvCategory.text = category
            binding.root.setOnClickListener {
                onClick(name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor?.apply {
            moveToPosition(position)
            val name = getString(getColumnIndexOrThrow(PlaceContract.COLUMN_TITLE))
            val address = getString(getColumnIndexOrThrow(PlaceContract.COLUMN_LOCATION))
            val category = getString(getColumnIndexOrThrow(PlaceContract.COLUMN_CATEGORY))
            holder.bind(name, address, category)
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
