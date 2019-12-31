package com.example.adapters



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.entities.Song
import com.example.utilis.DataChange

class GenAdapter2<T, V : RecyclerView.ViewHolder>(
    var list: List<T>,
    private var vh: (View) -> V,
    private var layoutRes: Int,
    private var vhBinder: (Int, V) -> Unit
) : RecyclerView.Adapter<V>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {

        return vh(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: V, position: Int) {

        vhBinder(position, holder)

    }

    /*fun dataUpdate(position: Int, data: T) {
        list[position] = data
        notifyItemChanged(position)
    }

    fun dataDelete(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun dataInsert(data: T) {
        list.add(data)
        notifyItemInserted(list.lastIndex)
    }*/


}