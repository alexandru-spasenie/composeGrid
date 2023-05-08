package com.compose.gridapp.events

import com.compose.gridapp.models.GridItem

sealed class GridUiEvent {
    data class OnGridItemClickedEvent(val gridItem: GridItem): GridUiEvent()
    object OnMergeButtonClickedEvent: GridUiEvent()
}
