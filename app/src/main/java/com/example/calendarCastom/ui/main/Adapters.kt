package com.example.calendarCastom.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarCastom.EntityDataBase
import com.example.calendarCastom.MainViewModel
import com.example.calendarCastom.R

class EventAdapter(private var data: List<EntityDataBase>, private var context: Context, val viewModel: MainViewModel): RecyclerView.Adapter<EventViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getItemCount(): Int  = data.size

    private fun getItem(position: Int): EntityDataBase = data[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(inflater.inflate(R.layout.event_instance, parent, false),context, viewModel)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun changeData(newData: List<EntityDataBase>){
        data = newData
        notifyDataSetChanged()
    }
}