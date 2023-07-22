package com.example.calendarCastom.ui.main

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarCastom.Fragment_events
import com.example.calendarCastom.R

class Main : Fragment() {
    var fragment_events: Fragment_events? = null
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = Main()
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
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setDaysText()
        setActiveDay(1,1)
        val list = view.findViewById<RecyclerView>(R.id.eventList)
        var data = viewModel.getData()
        var adapter: EventAdapter = EventAdapter(data, view.context, viewModel)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(view.context)
        view.findViewById<TextView>(R.id.month).text = viewModel.getMonth()
        view.findViewById<TextView>(R.id.year).text = viewModel.year.toString()
        for(week in 0..5){
            for(day in 0..6){
                view.findViewWithTag<TextView>("day${week}_${day}").setOnClickListener{
                    setActiveDay(week, day)
                    viewModel.updateEvents(view.context)
//                    adapter.changeData(viewModel.getData())
                }
            }
        }
        view.findViewById<ImageView>(R.id.prevMonth).setOnClickListener {viewModel.prevMonth()}
        view.findViewById<ImageView>(R.id.nextMonth).setOnClickListener {viewModel.nextMonth()}
        view.findViewById<ImageView>(R.id.prevYear).setOnClickListener {viewModel.prevYear()}
        view.findViewById<ImageView>(R.id.nextYear).setOnClickListener {viewModel.nextYear()}
        view.findViewById<ImageView>(R.id.new_event_button).setOnClickListener{
            if(viewModel.getDay() != null)
                fragment_events?.beginFragment()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.month.observe(this, Observer<Int>{ month ->
            setDaysText()
            view?.findViewById<TextView>(R.id.month)?.text = viewModel.getMonth()
            view?.findViewById<TextView>(R.id.year)?.text = viewModel.getYear().toString()
        })
        viewModel.year.observe(this, Observer<Int>{ year ->
            setDaysText()
            view?.findViewById<TextView>(R.id.month)?.text = viewModel.getMonth()
            view?.findViewById<TextView>(R.id.year)?.text = viewModel.getYear().toString()
        })
        viewModel.eventData.observe(this, Observer<List<EntityDataBase>>{ newList ->
            Log.d("observe", "update")
            val adapter: EventAdapter
            if(view?.findViewById<RecyclerView>(R.id.eventList)?.adapter is EventAdapter) {
                adapter = view?.findViewById<RecyclerView>(R.id.eventList)?.adapter as EventAdapter
                adapter.changeData(newList)
            }
        })
    }

    fun setDaysText(){
        viewModel.updateViewText()
        for(week in 0..5){
            for(day in 0..6){
                view?.findViewWithTag<TextView>("day${week}_${day}")?.text = viewModel.viewText[week][day]
            }
        }
    }

    fun setActiveDay(weekNumber :Int,dayNumber: Int){
        view?.findViewWithTag<TextView>("day${viewModel.weekNumber}_${viewModel.dayNumber}")?.setBackgroundColor(Color.BLACK)
        viewModel.dayNumber = dayNumber
        viewModel.weekNumber = weekNumber
        view?.findViewWithTag<TextView>("day${weekNumber}_${dayNumber}")?.setBackgroundColor(Color.GRAY)
    }
}