package com.mindorks.framework.whereismy.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.whereismy.activities.MainActivity
import com.mindorks.framework.whereismy.adapters.ItemAdapter
import com.mindorks.framework.whereismy.databinding.FragmentItemListBinding
import com.mindorks.framework.whereismy.listeners.OnListItemClickListener
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder

class ItemsListFragment : Fragment() {

    private lateinit var itemListBinding: FragmentItemListBinding
    private lateinit var onListItemClickListener: OnListItemClickListener


  private val itemsRepostiory: ItemDao by  lazy {
      ItemsDatabaseBuilder.getInstance().itemDao()
  }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        itemListBinding= FragmentItemListBinding.inflate(
                inflater,
                container,
                false)
        setupRecyclerView()

        itemListBinding.backButton.setOnClickListener{
            goBack()
        }
        return itemListBinding.root
    }

    private fun goBack(){
        requireActivity().run{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnListItemClickListener){
            onListItemClickListener=context
        }

    }

    override fun onResume() {
        super.onResume()
        (itemListBinding.rvItem.adapter as ItemAdapter).refreshData(itemsRepostiory.getItems())
    }

    private fun setupRecyclerView(){
        itemListBinding.rvItem.layoutManager=LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )

        itemListBinding.rvItem.adapter=ItemAdapter(itemsRepostiory.getItems(),onListItemClickListener)
    }

    companion object{
        const val TAG="Item list"
        fun create():ItemsListFragment{
            return ItemsListFragment()
        }


    }
}