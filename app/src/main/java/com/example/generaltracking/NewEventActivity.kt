package com.example.generaltracking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.Calendar
import java.util.Locale
import kotlin.text.*

class NewEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val inputField: EditText = findViewById(R.id.inputField)
        val dateTimePicker: Button = findViewById(R.id.dateTimePicker)
        val saveButton: Button = findViewById(R.id.saveEventButton)

        val jsonData: JSONObject = FileHelper.loadData(this)
        val categories = EventParser.parseCategories(jsonData)

        val categoryNames = categories.map { it }
        categorySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryNames)

        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dateTimePicker.text = currentDateTime

        saveButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            val inputValue = inputField.text.toString()
            val selectedDateTime = dateTimePicker.text.toString()

            val newEvent = Event(UUID.randomUUID().toString(), selectedDateTime, selectedCategory, inputValue)
            EventParser.addEvent(this, newEvent)
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