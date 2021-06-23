package com.mindorks.framework.whereismy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.whereismy.R
import com.mindorks.framework.whereismy.listeners.OnListItemClickListener
import com.mindorks.framework.whereismy.model.Item

class ItemAdapter(
        items:List<Item>,
        private val listener:OnListItemClickListener
):
    RecyclerView.Adapter<ItemViewHolder>(){

    private val items:MutableList<Item> = mutableListOf()

    init{
        refreshData(items)
    }

       fun refreshData(item:List<Item>){
        this.items.clear()
        this.items.addAll(item)
        this.notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemsView=LayoutInflater.from(parent.context)
                .inflate(R.layout.item,parent,false)
        return ItemViewHolder(itemsView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item=items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{listener.onListItemClick(item)}
    }

    override fun getItemCount(): Int =items.size


}