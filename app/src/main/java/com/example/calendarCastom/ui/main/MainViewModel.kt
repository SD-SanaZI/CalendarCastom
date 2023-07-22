package com.example.calendarCastom.ui.main

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    private var _eventData: MutableLiveData<List<EntityDataBase>> = MutableLiveData<List<EntityDataBase>>(listOf<EntityDataBase>())
    var eventData: LiveData<List<EntityDataBase>> = _eventData
    var viewText : Array<Array<String>> = Array(6){i -> Array(7){j -> " "}}
    var dayNumber: Int = 0
    var weekNumber: Int = 0
    private val _year: MutableLiveData<Int> = MutableLiveData<Int>(2023)
    val year : LiveData<Int> = _year
    private val _month: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val month : LiveData<Int> = _month

    fun setMonth(value: Int){
        if(value in 0..11)
            _month.value = value
        else
            _month.value = 1
    }

    fun updateViewText(){
        val date = Calendar.getInstance()
        date.set(Calendar.DAY_OF_MONTH, 1)
        date.set(Calendar.MONTH,month.value ?: 0)
        date.set(Calendar.YEAR, year.value ?: 2023)
        val firstDay = date.get(Calendar.DAY_OF_WEEK) -1
        val lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH)
        for(week in 0..5){
            for(day in 0..6){
                if(1 + week * 7 + day - firstDay in 1..lastDay)
                    viewText[week][day] = (1 + week * 7 + day - firstDay).toString()
                else
                    viewText[week][day] = "+"
            }
        }
    }

    private val monthName = listOf<String>("January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December")

    fun updateEvents(context: Context){
        if(viewText[weekNumber][dayNumber] != "+") {
            val db = DataBaseRoom.getInstance(context)
            _eventData.value = db.dao().getDayEvents(
                _year.value ?: 2023,
                _month.value ?: 7,
                viewText[weekNumber][dayNumber].toInt()
            )
            Log.d("updateEvent ${_year.value ?: 2023} ${_month.value ?: 7} ${viewText[weekNumber][dayNumber].toInt()}", "${_eventData.value.toString()} ")
        }
    }

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

    fun nextMonth(){
        if(_month.value != 11)
            _month.value = _month.value?.plus(1)
        else {
            _month.value = 0
            nextYear()
        }
    }

    fun prevMonth(){
        if(_month.value != 0)
            _month.value = _month.value?.minus(1)
        else {
            _month.value = 11
            prevYear()
        }
    }

    fun nextYear(){
        _year.value = _year.value?.plus(1)
    }

    fun prevYear(){
        _year.value = _year.value?.minus(1)
    }

    fun getYear(): Int {
        return _year.value ?: 2023
    }

    fun getMonth(): String {
        return monthName[_month.value ?: 0]
    }

    fun getMonthInt():Int{
        return _month.value ?: 0
    }

    fun getDay(): Int?{
        if(viewText[weekNumber][dayNumber] != "+")
            return viewText[weekNumber][dayNumber].toInt()
        else
            return null
    }
/*
    fun setData(data : List<EntityDataBase>){
        _eventData.value = data
    }
*/
    fun getData():List<EntityDataBase>{
        return _eventData.value ?: listOf<EntityDataBase>()
    }
}