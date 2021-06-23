package com.mindorks.framework.whereismy.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mindorks.framework.whereismy.R
import com.mindorks.framework.whereismy.databinding.ActivityItemListBinding
import com.mindorks.framework.whereismy.fragments.ItemDetailsFragment
import com.mindorks.framework.whereismy.fragments.ItemsListFragment
import com.mindorks.framework.whereismy.listeners.ItemDetailsFragmentOnClickListener
import com.mindorks.framework.whereismy.listeners.OnListItemClickListener
import com.mindorks.framework.whereismy.model.Item
import com.mindorks.framework.whereismy.persistence.ItemDao
import com.mindorks.framework.whereismy.persistence.ItemsDatabaseBuilder

class ItemListActivity :AppCompatActivity(),OnListItemClickListener, ItemDetailsFragmentOnClickListener{

    private lateinit var itemListBinding : ActivityItemListBinding
    private var itemListFragment=ItemsListFragment.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemListBinding= ActivityItemListBinding.inflate(layoutInflater)
        itemListBinding.fabAddItem.setOnClickListener{
            createNewItem()
        }
        setContentView(itemListBinding.root)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.fl_fragmentContainer,ItemsListFragment.create(),ItemsListFragment.TAG)
                    .commit()
        }
    }

    private fun createNewItem(){
        val newItemIntent= Intent(this,NewItemActivity::class.java)
        startActivity(newItemIntent)
    }


    override fun onListItemClick(item: Item) {
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.fl_fragmentContainer,
                        ItemDetailsFragment.create(item),
                        ItemDetailsFragment.TAG
                )
                .addToBackStack(null)
                .commit()
    }

    override fun onDeleteButtonClick() {
        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.fl_fragmentContainer,itemListFragment,
                        ItemsListFragment.TAG
                )
                .addToBackStack(null)
                .commit()
    }

}