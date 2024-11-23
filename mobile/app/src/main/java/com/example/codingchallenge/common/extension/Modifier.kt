package com.example.codingchallenge.common.extension

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.defaultButtonSize(): Modifier {
    return this.width(250.dp).height(50.dp)
}