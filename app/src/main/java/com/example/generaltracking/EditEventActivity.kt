package com.example.generaltracking

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class EditEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val eventId = intent.getStringExtra("EVENT_ID") ?: return finish()

        val jsonData: JSONObject = FileHelper.loadData(this)
        val event = EventParser.getEventById(jsonData, eventId) ?: return finish()

        val inputField: EditText = findViewById(R.id.inputField)
        val dateTimePicker: EditText = findViewById(R.id.dateTimePicker)
        val saveButton: Button = findViewById(R.id.saveEventButton)

        inputField.setText(event.value.toString())
        dateTimePicker.setText(event.timestamp)

        saveButton.setOnClickListener {
            event.value = inputField.text.toString()
            event.timestamp = dateTimePicker.text.toString()
            EventParser.updateEvent(this, event)
            finish()
        }
    }
}