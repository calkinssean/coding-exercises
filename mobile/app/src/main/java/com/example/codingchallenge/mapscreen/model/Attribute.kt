package com.example.codingchallenge.mapscreen.model

import com.example.codingchallenge.common.composables.CSFlowRowItem

data class Attribute(
    val type: String,
    val value: String
): CSFlowRowItem {
    override fun displayValue() = value
}

