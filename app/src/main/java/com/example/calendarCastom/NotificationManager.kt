package com.example.calendarCastom

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*


class NotificationManager {
    val mChannel = NotificationChannelCompat.Builder("my_channel_01", NotificationManagerCompat.IMPORTANCE_DEFAULT).apply {
        setName("channel name") // Must set! Don't remove
        setDescription("channel description")
        setLightsEnabled(true)
        setLightColor(Color.RED)
        setVibrationEnabled(true)
        setVibrationPattern(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
    }.build()

    fun updateNotification(context: Context){
        val date = Calendar.getInstance()
        val db = DataBaseRoom.getInstance(context)
        val events = db.dao().getDayEvents(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(
            Calendar.DAY_OF_MONTH))
        for(event in events){
            val eventDate = Calendar.getInstance()
            eventDate.set(Calendar.DAY_OF_MONTH, event.day)
            eventDate.set(Calendar.MONTH,event.month)
            eventDate.set(Calendar.YEAR, event.year)
            eventDate.set(Calendar.HOUR_OF_DAY, event.hour)
            eventDate.set(Calendar.MINUTE, event.minute)
            val time = eventDate.timeInMillis
            val i = Intent(context, NotificationReceiver::class.java)
            i.putExtra("ARG_TEXT", event.text)
            i.putExtra("ARG_ID", event.id ?: 0)
            val pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_MUTABLE)
            val alarm = context.getSystemService(Context.ALARM_SERVICE)  as AlarmManager
            alarm.set(AlarmManager.RTC_WAKEUP, time, pi)
            Log.d("Разница","${System.currentTimeMillis()} $time")
        }
    }

    fun setNotification(context: Context, text: String, id: Int){
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(mChannel)
        val notification = NotificationCompat.Builder(context,  "my_channel_01")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Event Started")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify("celendar_natif",id, notification)
        Log.d("notification","add")
    }
}