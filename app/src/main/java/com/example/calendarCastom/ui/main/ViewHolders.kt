package com.example.calendarCastom.ui.main


import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarCastom.DBManager
import com.example.calendarCastom.EntityDataBase
import com.example.calendarCastom.MainViewModel
import com.example.calendarCastom.R

class EventViewHolder(view: View, private val context: Context, private val viewModel: MainViewModel): RecyclerView.ViewHolder(view){
    private val hour: TextView = itemView.findViewById(R.id.timeHour)
    private val minute: TextView = itemView.findViewById(R.id.timeMinute)
    private val text: TextView = itemView.findViewById(R.id.eventText)
    private val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)

    fun bind(data: EntityDataBase){
        hour.text = data.hour.toString()
        minute.text = data.minute.toString()
        text.text = data.text
        deleteButton.setOnClickListener{
            DBManager().deleteEvent(context, data.id)
            viewModel.updateEvents(context)
        }
    }
}