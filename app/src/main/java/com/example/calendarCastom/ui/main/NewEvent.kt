package com.example.calendarCastom.ui.main

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
import androidx.lifecycle.ViewModelProvider
import com.example.calendarCastom.Fragment_events
import com.example.calendarCastom.R

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
        view.findViewById<TextView>(R.id.saveEventButton).setOnClickListener {
            val day = viewModel.getDay()
            if (day != null){
                viewModel.addEvent(
                    view.context, EntityDataBase(
                        year = viewModel.getYear(),
                        month = viewModel.getMonthInt(),
                        day = day,
                        hour = view.findViewById<EditText>(R.id.edit_hour).text.toString().toInt(),
                        minute = view.findViewById<EditText>(R.id.edit_minute).text.toString()
                            .toInt(),
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