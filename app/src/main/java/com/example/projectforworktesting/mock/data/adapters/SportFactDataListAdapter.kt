package com.example.projectforworktesting.mock.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectforworktesting.R
import com.example.projectforworktesting.databinding.SportFactsListItemBinding
import com.example.projectforworktesting.mock.data.room.SportFactData

class SportFactDataListAdapter : RecyclerView.Adapter<SportFactDataListAdapter.ViewHolder>() {

    var items: List<SportFactData> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClick: (SportFactData) -> Unit = {}
    fun itemClick(listener: (SportFactData) -> Unit) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sport_facts_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.fact = items[position]
        holder.itemView.setOnClickListener {
            itemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = SportFactsListItemBinding.bind(view)

    }
}