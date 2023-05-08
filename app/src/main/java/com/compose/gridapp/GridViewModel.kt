package com.compose.gridapp

import androidx.lifecycle.ViewModel
import com.compose.gridapp.events.GridUiEvent
import com.compose.gridapp.models.GridItem
import com.compose.gridapp.models.GridModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This constant value can be changed to whatever else maximum items number of the grid
 */
const val MAXIMUM_ITEMS = 10
class GridViewModel : ViewModel() {

    private val _gridUiState = MutableStateFlow(GridModel())
    val gridUiState = _gridUiState.asStateFlow()

    init {

        val initialItems = arrayListOf<GridItem>()
        for (i in 0..MAXIMUM_ITEMS) {
            initialItems.add(GridItem(name = "Item $i"))
        }
        updateUiState(gridItemList = initialItems)
    }


    fun onEvent(event: GridUiEvent) {
        when (event) {
            is GridUiEvent.OnGridItemClickedEvent -> {
                val gridItemList = gridUiState.value.gridItemList.toMutableList()
                val index = gridItemList.indexOf(event.gridItem)
                gridItemList[index] =
                    gridItemList[index].copy(selected = !gridItemList[index].selected)
                val canMergeItems = checkIfCanMerge(gridItemList)
                updateUiState(gridItemList = gridItemList, canMergeItems = canMergeItems)
            }
            GridUiEvent.OnMergeButtonClickedEvent -> {
                onMergeButtonClicked()
            }
        }
    }


    private fun updateUiState(
        gridItemList: MutableList<GridItem> = _gridUiState.value.gridItemList,
        canMergeItems: Boolean = _gridUiState.value.canMergeItems
    ) {
        _gridUiState.update {
            it.copy(
                gridItemList = gridItemList,
                canMergeItems = canMergeItems
            )
        }
    }


    private fun onMergeButtonClicked() {
        val gridItemList = ArrayList<GridItem>()
        gridItemList.addAll(gridUiState.value.gridItemList)
        if (gridUiState.value.canMergeItems) {
            var firstItem: GridItem? = null
            val mergingItems = mutableListOf<GridItem>()
            for (item in gridItemList) {
                if (item.selected) {
                    if (firstItem == null) {
                        firstItem = item
                        item.selected = false
                    } else {
                        mergingItems.add(item)
                    }
                }
            }
            gridItemList.removeAll(mergingItems.toSet())
            updateUiState(gridItemList = gridItemList, canMergeItems = false)
        }
    }


    private fun checkIfCanMerge(itemsClicked: MutableList<GridItem>): Boolean {
        var totalSelectedItems = 0
        for (item in itemsClicked) {
            if (item.selected)
                totalSelectedItems++
        }
        return totalSelectedItems > 1
    }
}