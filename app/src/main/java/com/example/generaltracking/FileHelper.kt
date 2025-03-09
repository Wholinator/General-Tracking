package com.example.generaltracking

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


object FileHelper {
    private const val FILE_NAME = "events_data.json"

    fun loadData(context: Context): JSONObject {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) {
                // Create a default JSON structure if file doesn't exist
                JSONObject().apply {
                    put("categories", JSONArray())
                    put("events", JSONArray())
                }
            } else {
                val content = file.readText()
                JSONObject(content)
            }
        } catch (e: Exception) {
            // Handle error appropriately (log or show message)
            JSONObject().apply {
                put("categories", JSONArray())
                put("events", JSONArray())
            }
        }
    }

    fun saveData(context: Context, data: JSONObject) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            file.writeText(data.toString(4)) // 4-space indentation for readability
        } catch (e: Exception) {
            // Handle error appropriately
            e.printStackTrace()
        }
    }
}
