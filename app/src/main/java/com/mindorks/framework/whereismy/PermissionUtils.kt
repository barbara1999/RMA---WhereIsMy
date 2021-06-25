package com.mindorks.framework.whereismy

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.hasPermissionCompact (permission : String):Boolean{
    return ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_GRANTED
}

fun AppCompatActivity.shouldShowRationaleCompact(permission: String):Boolean{
    return ActivityCompat.shouldShowRequestPermissionRationale(this,permission)
}

fun AppCompatActivity.requestPermissionCompact(permission : Array<String>,requestCode :Int){
    ActivityCompat.requestPermissions(this,permission,requestCode)
}