package com.example.codingchallenge.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.common.extension.defaultButtonSize
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    subtitleText: String? = null,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = errorText ?: stringResource(R.string.generic_error_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        Text(
            text = subtitleText ?: stringResource(R.string.generic_error_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        CSButton(
            modifier = Modifier
                .padding(top = 100.dp)
                .defaultButtonSize(),
            text = stringResource(R.string.retry),
            onClick = onRetry
        )
    }
}

@Preview
@Composable
fun ErrorScreenPreview_CustomText() {
    CodingChallengeTheme {
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            errorText = "Oops",
            subtitleText = "Looks like stuff is broken",
            onRetry = {})
    }
}

@Preview
@Composable
fun ErrorScreenPreview_DefaultText() {
    CodingChallengeTheme {
        ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            onRetry = {}
        )
    }
}