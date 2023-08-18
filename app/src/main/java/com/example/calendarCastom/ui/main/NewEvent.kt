package com.example.calendarCastom.ui.main

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.calendarCastom.Fragment_events
import com.example.calendarCastom.R
import java.text.SimpleDateFormat
import java.util.*

class NewEvent : Fragment() {
    var fragment_events: Fragment_events? = null
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = NewEvent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Fragment_events)
            fragment_events = context
    }

    override fun onDetach() {
        super.onDetach()
        fragment_events =  null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_event, container, false)
        val time: EditText = view.findViewById<EditText>(R.id.edit_time)
        time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                time.setText(SimpleDateFormat("HH:mm").format(cal.time).toString())
            }
            TimePickerDialog(view.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        view.findViewById<TextView>(R.id.saveEventButton).setOnClickListener {
            val day = viewModel.getDay()
            var hour = "00"
            var minute = "00"
            if(time.text.matches(Regex("[0-9]+:[0-9]+"))){
                hour = time.text.split(':')[0]
                minute = time.text.split(':')[1]
            }
            else{
                hour = "25"//Для непрохождения следующего условия
                Toast.makeText(view.context,"Invalid time input type. Acceptable type HH:mm",Toast.LENGTH_LONG).show()
            }
            if (day != null && hour.toInt() in 0..23 && minute.toInt() in 0..59){
                viewModel.addEvent(
                    view.context, EntityDataBase(
                        year = viewModel.getYear(),
                        month = viewModel.getMonthInt(),
                        day = day,
                        hour = hour.toInt(),
                        minute = minute.toInt(),
                        text = view.findViewById<EditText>(R.id.edit_text).text.toString()
                    )
                )
            }
            fragment_events?.finishFragment()
            viewModel.updateEvents(view.context)
        }
        return view
    }
}