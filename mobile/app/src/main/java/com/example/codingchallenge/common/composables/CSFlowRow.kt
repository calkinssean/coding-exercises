package com.example.codingchallenge.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CSFlowRow(
    modifier: Modifier = Modifier,
    items: List<CSFlowRowItem>,
    selectedItems: List<CSFlowRowItem>,
    onItemClicked: (CSFlowRowItem) -> Unit
) {
    FlowRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            CSFlowRowUIItem(
                item,
                selected = selectedItems.contains(item),
                onClick = { onItemClicked(it) }
            )
        }
    }
}

@Composable
private fun CSFlowRowUIItem(item: CSFlowRowItem, selected: Boolean = false, onClick: (CSFlowRowItem) -> Unit) {
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = if (selected) Color.Blue else Color.LightGray, shape = RoundedCornerShape(12.dp))
            .clickable { onClick(item) }) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
            text = item.displayValue(),
            color = Color.LightGray
        )
    }
}

interface CSFlowRowItem {
    fun displayValue(): String
}

@Preview
@Composable
private fun CSFlowRowPreview() {
    val selectedItem = PreviewItem("Selected Item")
    val items = listOf(PreviewItem("Item 1"), PreviewItem("Item 2"), PreviewItem("Item 3"), selectedItem)
    CSFlowRow(modifier = Modifier.fillMaxWidth(), items = items, selectedItems = listOf(selectedItem), onItemClicked = {})
}

private data class PreviewItem(val value: String) : CSFlowRowItem {
    override fun displayValue(): String = value

}