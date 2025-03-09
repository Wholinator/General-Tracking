package com.example.generaltracking

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class NewEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val inputField: EditText = findViewById(R.id.inputField)
        val dateTimePicker: EditText = findViewById(R.id.dateTimePicker)
        val saveButton: Button = findViewById(R.id.saveEventButton)

        val jsonData: JSONObject = FileHelper.loadData(this)
        val categories = EventParser.parseCategories(jsonData)

        val categoryNames = categories.map { it.name }
        categorySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryNames)

        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dateTimePicker.setText(currentDateTime)

        saveButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            val inputValue = inputField.text.toString()
            val selectedDateTime = dateTimePicker.text.toString()

            val newEvent = Event(UUID.randomUUID().toString(), selectedDateTime, selectedCategory, inputValue)
            EventParser.addEvent(this, newEvent)
            finish()
        }
    }
}