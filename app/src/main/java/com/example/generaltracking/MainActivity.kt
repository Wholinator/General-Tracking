package com.example.generaltracking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventListView: RecyclerView = findViewById(R.id.eventList)
        val newEventButton: Button = findViewById(R.id.newEventButton)

        eventListView.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(loadEvents())
        eventListView.adapter = eventAdapter

        newEventButton.setOnClickListener {
            val intent = Intent(this, NewEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        eventAdapter.updateEvents(loadEvents())
    }

    private fun loadEvents(): List<Event> {
        val jsonData: JSONObject = FileHelper.loadData(this)
        return EventParser.parseEvents(jsonData)
    }
}