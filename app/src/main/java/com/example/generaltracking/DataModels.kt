package com.example.generaltracking

data class Category(
    val name: String,
    val type: String, // "text", "select", or "number"
    val options: List<String>? = null // For "select" type
)

data class Event(
    val id: String,
    var timestamp: String, // ISO 8601 format, e.g., "2025-03-09T14:30:00Z"
    val category: String,  // Should match one of the defined categories
    var value: Any,        // Could be String, Number, etc. based on category type
    var notes: String? = null
)
