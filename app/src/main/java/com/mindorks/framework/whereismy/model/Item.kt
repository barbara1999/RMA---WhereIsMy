package com.mindorks.framework.whereismy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Item(
        @ColumnInfo(name="name")val name:String,
        @ColumnInfo(name="personName")val personName:String,
        @ColumnInfo(name="date")val date:String,
        @ColumnInfo(name="phoneNumber")val phoneNumber:Int,
        @ColumnInfo(name="longitude")val longitude:Int,
        @ColumnInfo(name="latitude")val latitude:Int,
        @PrimaryKey(autoGenerate = true)val id:Int
):Serializable


