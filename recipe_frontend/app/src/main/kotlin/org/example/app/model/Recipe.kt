package org.example.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * PUBLIC_INTERFACE
 * A simple data model representing a Recipe with essential fields.
 */
@Parcelize
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageResName: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val timeMinutes: Int,
) : Parcelable
