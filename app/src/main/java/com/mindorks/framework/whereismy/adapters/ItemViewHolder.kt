package com.mindorks.framework.whereismy.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.whereismy.databinding.ItemBinding
import com.mindorks.framework.whereismy.model.Item

class ItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView){
    fun bind(item: Item){
        val itemBinding=ItemBinding.bind(itemView)
        itemBinding.itemName.text=item.name
        itemBinding.person.text=item.personName
        itemBinding.date.text=item.date
        itemBinding.phoneNumber.text=item.phoneNumber.toString()
    }
}