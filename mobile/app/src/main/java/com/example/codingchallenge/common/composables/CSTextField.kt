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
import com.example.codingchallenge.ui.theme.TextFieldBackground
import com.example.codingchallenge.ui.theme.TextFieldTextColor

@Composable
fun CSTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        modifier = modifier,
        value = value,
        shape = RoundedCornerShape(12.dp),
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = TextFieldBackground,
            unfocusedContainerColor = TextFieldBackground,
            focusedLeadingIconColor = TextFieldTextColor,
            unfocusedLeadingIconColor = TextFieldTextColor,
            unfocusedTextColor = TextFieldTextColor,
            focusedTextColor = TextFieldTextColor,
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