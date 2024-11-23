package com.example.codingchallenge.common.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.common.Displayable
import com.example.codingchallenge.ui.theme.Blue500
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Displayable> CSFlowRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItems: List<T>,
    onItemClicked: (T) -> Unit,
    onClearAllClicked: () -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            CSFlowRowUIItem(
                item,
                selected = selectedItems.contains(item),
                onClick = { onItemClicked(it) }
            )
        }
        if (selectedItems.isNotEmpty()) {
            ClearFiltersItem { onClearAllClicked() }
        }
    }
}

@Composable
private fun ClearFiltersItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .border(width = 1.dp, color = Blue500, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(18.dp),
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "",
            tint = Blue500
        )
    }
}

@Composable
private fun <T : Displayable> CSFlowRowUIItem(
    item: T,
    selected: Boolean = false,
    onClick: (T) -> Unit
) {
    val contentColor = if (selected) Blue500 else Color.LightGray
    Box(
        modifier = Modifier
            .height(30.dp)
            .border(width = 1.dp, color = contentColor, shape = RoundedCornerShape(12.dp))
            .clickable { onClick(item) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = item.displayValue(),
            color = contentColor
        )
    }
}

@Preview
@Composable
private fun CSFlowRowPreview() {
    val selectedItem = PreviewItem("Selected Item")
    val items =
        listOf(PreviewItem("Item 1"), PreviewItem("Item 2"), PreviewItem("Item 3"), selectedItem)
   CodingChallengeTheme {
       CSFlowRow(
           modifier = Modifier.fillMaxWidth(),
           items = items,
           selectedItems = listOf(selectedItem),
           onItemClicked = {}
       )
   }
}

private data class PreviewItem(val value: String) : Displayable {
    override fun displayValue(): String = value

}