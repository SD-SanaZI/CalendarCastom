package com.example.calendarCastom

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {

    companion object{
        const val ARG_TEXT = "ARG_TEXT"
        const val ARG_ID = "ARG_ID"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra(ARG_TEXT)
        val id = intent.getIntExtra(ARG_ID,0)
        NotificationManager().setNotification(context,text?:"Simple message",id)
        Log.d("Receiver","Start notification")
    }
}