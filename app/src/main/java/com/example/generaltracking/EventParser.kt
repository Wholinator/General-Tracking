package com.example.generaltracking

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object EventParser {

    fun parseEvents(jsonData: JSONObject): List<Event> {
        val events = mutableListOf<Event>()
        val eventArray = jsonData.optJSONArray("events") ?: JSONArray()

        for (i in 0 until eventArray.length()) {
            val obj = eventArray.getJSONObject(i)
            events.add(Event(
                id = obj.getString("id"),
                timestamp = obj.getString("timestamp"),
                category = obj.getString("category"),
                value = obj.getString("value")
            ))
        }
        return events
    }

    fun addEvent(context: Context, event: Event) {
        val jsonData = FileHelper.loadData(context)
        val eventArray = jsonData.optJSONArray("events") ?: JSONArray()
        val eventObj = JSONObject().apply {
            put("id", event.id)
            put("timestamp", event.timestamp)
            put("category", event.category)
            put("value", event.value)
        }
        eventArray.put(eventObj)
        jsonData.put("events", eventArray)
        FileHelper.saveData(context, jsonData)
    }

    fun getEventById(jsonData: JSONObject, id: String): Event? {
        val eventArray = jsonData.optJSONArray("events") ?: JSONArray()
        for (i in 0 until eventArray.length()) {
            val obj = eventArray.getJSONObject(i)
            if (obj.getString("id") == id) {
                return Event(
                    id = obj.getString("id"),
                    timestamp = obj.getString("timestamp"),
                    category = obj.getString("category"),
                    value = obj.getString("value")
                )
            }
        }
        return null
    }

    fun updateEvent(context: Context, updatedEvent: Event) {
        val jsonData = FileHelper.loadData(context)
        val eventArray = jsonData.optJSONArray("events") ?: JSONArray()

        for (i in 0 until eventArray.length()) {
            val obj = eventArray.getJSONObject(i)
            if (obj.getString("id") == updatedEvent.id) {
                obj.put("timestamp", updatedEvent.timestamp)
                obj.put("value", updatedEvent.value)
                break
            }
        }

        jsonData.put("events", eventArray)
        FileHelper.saveData(context, jsonData)
    }
}
