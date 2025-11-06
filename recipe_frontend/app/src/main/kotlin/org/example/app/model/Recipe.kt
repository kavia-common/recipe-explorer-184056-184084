package org.example.app.model

/**
 * PUBLIC_INTERFACE
 * A simple data model representing a Recipe with essential fields.
 * Note: Parcelable is not required for current navigation; data passed via IDs.
 */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageResName: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val timeMinutes: Int,
)
