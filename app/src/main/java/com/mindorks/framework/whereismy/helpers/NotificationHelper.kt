package com.mindorks.framework.whereismy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

fun getChannelId(name: String): String = "${AppWhereIsMy.application.packageName}-$name"
const val CHANNEL_DATE = "Likes_Channel"
@RequiresApi(api = Build.VERSION_CODES.O)
fun createNotificationChannel(name: String, description: String, importance: Int): NotificationChannel {
    val channel = NotificationChannel(getChannelId(name), name, importance)
    channel.description = description
    channel.setShowBadge(true)
    return channel
}
@RequiresApi(api = Build.VERSION_CODES.O)
fun createNotificationChannels() {
    val channels = mutableListOf<NotificationChannel>()
    channels.add(createNotificationChannel(
            CHANNEL_DATE,
            "Likes on your posts",
            NotificationManagerCompat.IMPORTANCE_DEFAULT
    ))

    val notificationManager = AppWhereIsMy.application.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannels(channels)
}