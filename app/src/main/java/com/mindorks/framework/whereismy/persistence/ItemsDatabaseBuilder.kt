package com.mindorks.framework.whereismy.persistence

import androidx.room.Room
import com.mindorks.framework.whereismy.AppWhereIsMy

object ItemsDatabaseBuilder {

    private var instance:ItemsDatabase?=null

    fun getInstance():ItemsDatabase{
        synchronized(ItemsDatabase::class){
            if(instance==null){
                instance=buildDatabase()
            }
        }

        return instance!!
    }

    private fun buildDatabase():ItemsDatabase{
        return Room.databaseBuilder(
                AppWhereIsMy.application,ItemsDatabase::class.java,ItemsDatabase.NAME
        )
                .allowMainThreadQueries()
                .build()
    }
}