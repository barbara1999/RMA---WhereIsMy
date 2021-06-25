package com.mindorks.framework.whereismy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.whereismy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainBinding.addButton.setOnClickListener{ openAddActivity()}
        mainBinding.viewButton.setOnClickListener{openItemList()}
        mainBinding.viewMapButton.setOnClickListener{openMapActivity()}
        setContentView(mainBinding.root)
    }

    private fun openAddActivity(){
        val addNewIntent = Intent(this, NewItemActivity::class.java)
        startActivity(addNewIntent)
    }

    private fun openItemList(){
        val addNewIntent = Intent(this, ItemListActivity::class.java)
        startActivity(addNewIntent)
    }

    private fun openMapActivity(){
        val addNewIntent=Intent(this,MapActivity::class.java)
        startActivity(addNewIntent)
    }
}