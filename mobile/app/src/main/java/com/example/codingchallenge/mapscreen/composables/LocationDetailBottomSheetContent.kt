package com.example.codingchallenge.mapscreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.common.composables.CircularIcon
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import com.example.codingchallenge.ui.theme.TextFieldTextColor

@Composable
fun LocationDetailBottomSheetContent(modifier: Modifier = Modifier, location: Location) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIcon(
                modifier = Modifier.size(42.dp),
                resourceId = location.iconId,
                color = location.color
            )
            Column {
                Text(text = location.name, style = MaterialTheme.typography.titleLarge)
                Text(text = location.locationType.capitalize(Locale.current), style = MaterialTheme.typography.bodySmall, color = TextFieldTextColor)
            }
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = location.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(
                id = R.string.estimated_revenue,
                location.formattedRevenueString
            ), style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun LocationDetailBottomSheetContentPreview() {
    CodingChallengeTheme {
        LocationDetailBottomSheetContent(
            modifier = Modifier.fillMaxWidth(),
            location = Location(
                id = 0,
                latitude = 0.0,
                longitude = 0.0,
                attributes = listOf(
                    Attribute(type = "location_type", value = "restaurant"),
                    Attribute(type = "name", value = "The Steakhouse"),
                    Attribute(type = "description", value = "Premium steaks and fine dining."),
                    Attribute(type = "estimated_revenue_millions", value = "8.9")
                )
            )
        )
    }
}