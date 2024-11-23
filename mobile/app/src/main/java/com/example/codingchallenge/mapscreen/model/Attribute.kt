package com.example.codingchallenge.mapscreen.model

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.example.codingchallenge.common.Displayable

data class Attribute(
    val type: String,
    val value: String
): Displayable {
    override fun displayValue() = value.capitalize(Locale.current)
}

