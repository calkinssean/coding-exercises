package com.example.codingchallenge.common

enum class LoadState {
    Loading, None
}

interface Displayable {
    fun displayValue(): String
}