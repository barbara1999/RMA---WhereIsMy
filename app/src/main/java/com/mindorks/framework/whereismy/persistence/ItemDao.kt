package com.mindorks.framework.whereismy.persistence

import androidx.room.*
import com.mindorks.framework.whereismy.model.Item

@Dao
interface  ItemDao {
    @Query("SELECT * FROM items")
    fun getItems():MutableList<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item:Item)

    @Delete
    fun delete(item:Item)

    @Query("SELECT * FROM items WHERE id=:id")
    fun getItem(id:Int):Item
}