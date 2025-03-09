package com.example.generaltracking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.util.Calendar
import java.util.Locale

class EditEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val eventId = intent.getStringExtra("EVENT_ID") ?: return finish()

        val jsonData: JSONObject = FileHelper.loadData(this)
        val event = EventParser.getEventById(jsonData, eventId) ?: return finish()

        val inputField: EditText = findViewById(R.id.inputField)
        val dateTimePicker: Button = findViewById(R.id.dateTimePicker)
        val saveButton: Button = findViewById(R.id.saveEditButton)

        inputField.setText(event.value.toString())
        dateTimePicker.text = event.timestamp

        saveButton.setOnClickListener {
            event.value = inputField.text.toString()
            event.timestamp = dateTimePicker.text.toString()
            EventParser.updateEvent(this, event)
            finish()
        }

        dateTimePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, year, month, day ->
                val timePicker = TimePickerDialog(this, { _, hour, minute ->
                    val selectedDateTime = String.format(Locale.getDefault(), "%04d-%02d-%02d %02d:%02d", year, month + 1, day, hour, minute)
                    dateTimePicker.text = selectedDateTime
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                timePicker.show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
    }
}