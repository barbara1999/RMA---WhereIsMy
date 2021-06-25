package com.mindorks.framework.whereismy.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindorks.framework.whereismy.model.Item

@Database(entities = [Item::class],version = 3)
abstract class ItemsDatabase : RoomDatabase(){
    abstract fun itemDao():ItemDao
    companion object{
        const val NAME="itemsDb"
    }
}
