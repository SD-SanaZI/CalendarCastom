package com.example.calendarCastom

import android.content.Context
import android.util.Log

class DBManager {
    fun addEvent(context: Context, event: EntityDataBase){
        val db = DataBaseRoom.getInstance(context)
        val num = db.dao().insert(event)
        Log.d("addEvent", num.toString())
    }

    fun deleteEvent(context: Context, id: Int?){
        val db = DataBaseRoom.getInstance(context)
        db.dao().delete(id)
        Log.d("DeleteEvent", "done")
    }

    fun deleteEventsBase(context: Context){
        val db = DataBaseRoom.getInstance(context)
        db.dao().deleteAll()
        Log.d("DeleteEventBase", "done")
    }

    fun getEvents(context: Context, year: Int, month: Int, day: Int):List<EntityDataBase>{
        val db = DataBaseRoom.getInstance(context)
        return db.dao().getDayEvents(year, month,day)
    }
}