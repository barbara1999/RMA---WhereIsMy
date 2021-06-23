package com.mindorks.framework.whereismy

import android.app.Application

class AppWhereIsMy : Application() {

    companion object{
        lateinit var application : AppWhereIsMy
    }

    override fun onCreate() {
        super.onCreate()
        application=this
    }
}