package com.example.calendarCastom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.calendarCastom.ui.main.Main
import com.example.calendarCastom.ui.main.MainViewModel
import com.example.calendarCastom.ui.main.NewEvent

class MainActivity : AppCompatActivity(), Fragment_events {
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, Main.newInstance())
                .commitNow()
        }
    }

    override fun beginFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, NewEvent.newInstance(), "NewEventFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun finishFragment() {
        val fragment = supportFragmentManager.findFragmentByTag("NewEventFragment")
        if(fragment != null)
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
    }
}

interface Fragment_events{
    fun beginFragment()
    fun finishFragment()
}

//TODO сервис на уведомление при наступлении события