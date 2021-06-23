package com.mindorks.framework.whereismy.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindorks.framework.whereismy.databinding.FragmentItemDetailsBinding
import com.mindorks.framework.whereismy.listeners.ItemDetailsFragmentOnClickListener
import com.mindorks.framework.whereismy.model.Item
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder

class ItemDetailsFragment(private val item:Item) :Fragment(){

    private lateinit var itemDetailsBinding: FragmentItemDetailsBinding
    private lateinit var itemDetailsFragmentClickListener: ItemDetailsFragmentOnClickListener

    private val itemsRepository: ItemDao by  lazy {
        ItemsDatabaseBuilder.getInstance().itemDao()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    : View? {
        itemDetailsBinding= FragmentItemDetailsBinding.inflate(
                inflater,
                container,
                false
        )

        arguments?.let {
            val item=it.getSerializable(KEY_ITEM) as Item
            itemDetailsBinding.itemName.text=item.name
            itemDetailsBinding.person.text=item.personName
            itemDetailsBinding.phoneNumber.text=item.phoneNumber.toString()
            itemDetailsBinding.date.text=item.date


        }

        itemDetailsBinding.deleteButton.setOnClickListener{
            itemsRepository.delete(item)
            itemDetailsFragmentClickListener.onDeleteButtonClick()
        }

        return itemDetailsBinding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ItemDetailsFragmentOnClickListener){
            itemDetailsFragmentClickListener=context

        }
    }

    companion object{
        private const val KEY_ITEM="Item"
        const val TAG="Details item"

        fun create(item: Item): ItemDetailsFragment {
            val args = Bundle()
            args.putSerializable(KEY_ITEM, item)
            val fragment = ItemDetailsFragment(item)
            fragment.arguments = args
            return fragment
        }
    }

}