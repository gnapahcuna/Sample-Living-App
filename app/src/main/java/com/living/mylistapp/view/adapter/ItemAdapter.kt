package com.living.mylistapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.living.mylistapp.R
import com.living.mylistapp.model.Item
import com.living.mylistapp.view.holder.ItemViewHolder
import com.living.mylistapp.view.ui.DialogDelete

class ItemAdapter(private var context: Context, private var data: List<Item>, private val view: ItemListener): RecyclerView.Adapter<ItemViewHolder>(), Filterable{
    var dataFilter : List<Item> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        var holder = ItemViewHolder(v)
        return holder
    }

    override fun getItemCount(): Int {
        return dataFilter.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.nameView.text = dataFilter.get(position).itemName
        Glide.with(context)
            .load(dataFilter.get(position).imageUrl)
            .centerCrop()
            .into(holder.imgView)

        holder.deleteView.setOnClickListener { listener.onItemClicked(dataFilter.get(position).itemID) }
    }

    private lateinit var listener: ItemListener

    interface ItemListener {
        fun onItemClicked(itemID: Int)
    }

    fun setListener() {
        this.listener = view;
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilter = data
                } else {
                    val resultList = ArrayList<Item>()
                    for (row in data) {
                        if (row.itemName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    dataFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilter = results?.values as List<Item>
                notifyDataSetChanged()
            }

        }
    }
}