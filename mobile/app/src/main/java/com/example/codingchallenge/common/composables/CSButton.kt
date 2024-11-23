package com.example.codingchallenge.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.codingchallenge.common.extension.defaultButtonSize
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

@Composable
fun CSButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = text, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview
@Composable
fun MapAppButtonPreview() {
    CodingChallengeTheme {
        CSButton(modifier = Modifier.defaultButtonSize(), text = "Click Me!", onClick = {})
    }
}