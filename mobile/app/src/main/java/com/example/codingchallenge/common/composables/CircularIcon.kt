package com.example.codingchallenge.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R

@Composable
fun CircularIcon(modifier: Modifier = Modifier, color: Color, resourceId: Int) {
    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .border(width = 2.dp, color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(modifier = Modifier.fillMaxSize().padding(5.dp), painter = painterResource(resourceId), contentDescription = "", tint = Color.White)
    }
}

@Preview
@Composable
private fun CircularIconPreview() {
    CircularIcon(modifier = Modifier.size(24.dp), color = Color.Red, resourceId = R.drawable.ic_restaurant)
}