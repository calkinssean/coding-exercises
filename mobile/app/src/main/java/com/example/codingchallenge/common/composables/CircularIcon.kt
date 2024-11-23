package com.example.codingchallenge.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.ui.theme.Brown700
import com.example.codingchallenge.ui.theme.Cyan500
import com.example.codingchallenge.ui.theme.Pink500

@Composable
fun CircularIcon(modifier: Modifier = Modifier, color: Color, resourceId: Int, borderWidth: Dp = 4.dp) {
    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .border(width = borderWidth, color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.6f),
            painter = painterResource(resourceId),
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun CircularIconPreview() {
    Column {
        CircularIcon(
            modifier = Modifier.size(24.dp),
            color = Pink500,
            resourceId = R.drawable.ic_restaurant,
            borderWidth = 2.dp
        )
        CircularIcon(
            modifier = Modifier.size(48.dp),
            color = Brown700,
            resourceId = R.drawable.ic_cafe
        )
        CircularIcon(
            modifier = Modifier.size(100.dp),
            color = Cyan500,
            resourceId = R.drawable.ic_landmark,
            borderWidth = 8.dp
        )
    }
}