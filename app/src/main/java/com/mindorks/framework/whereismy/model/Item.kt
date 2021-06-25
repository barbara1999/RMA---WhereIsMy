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
        @ColumnInfo(name="phoneNumber")val phoneNumber:String,
        @ColumnInfo(name="longitude")val longitude:Double,
        @ColumnInfo(name="latitude")val latitude:Double,
        @ColumnInfo(name="address")val address:String,
        @PrimaryKey(autoGenerate = true)val id:Int
):Serializable


