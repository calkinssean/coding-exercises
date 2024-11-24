package com.example.codingchallenge.mapscreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import com.example.codingchallenge.ui.theme.TextFieldTextColor

@Composable
fun CSMapControl(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(32.dp)
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
    ) {
        Icon(modifier = Modifier.padding(4.dp), painter = painterResource(id = R.drawable.ic_map), tint = TextFieldTextColor, contentDescription = null)
        HorizontalDivider()
        Icon(modifier = Modifier.padding(4.dp), painter = painterResource(id = R.drawable.ic_location), tint = TextFieldTextColor, contentDescription = null)
    }
}

@Preview
@Composable
fun CSMapControlPreview() {
    CodingChallengeTheme {
        CSMapControl()
    }
}