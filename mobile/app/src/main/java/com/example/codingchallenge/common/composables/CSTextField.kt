package com.example.codingchallenge.common.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CSTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        shape = RoundedCornerShape(12.dp),
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.DarkGray,
            unfocusedTextColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun CSTextFieldPreview() {
    CSTextField(
        value = "Test",
        onValueChange = {},
        placeholder = { Text(text = "Placeholder") }
    )
}