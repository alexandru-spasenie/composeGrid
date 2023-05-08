package com.compose.gridapp.composeui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.gridapp.GridViewModel
import com.compose.gridapp.R
import com.compose.gridapp.events.GridUiEvent
import com.compose.gridapp.models.GridItem
import com.compose.gridapp.ui.theme.GridAppTheme

class GridComposeView {


    @Composable
    fun ShowGrid(gridViewModel: GridViewModel) {
        val gridUiState = gridViewModel.gridUiState.collectAsState().value
        val gridItems = gridUiState.gridItemList
        GridAppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column {
                    Box(
                        Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                    ) {
                        TitleText()
                        MergeButton(gridViewModel::onEvent, gridUiState.canMergeItems)
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = rememberLazyGridState(),
                        content = {
                            items(items = gridItems, key = {
                                it.name
                            }) { item ->
                                ItemView(gridViewModel::onEvent, gridItems[gridItems.indexOf(item)])
                            }
                        }
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyGridItemScope.ItemView(onUIEvent: (event: GridUiEvent) -> Unit, gridItem: GridItem) {
        Box(
            modifier = Modifier
                .animateItemPlacement()
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Text(
                text = gridItem.name,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .background(color = if (gridItem.selected) Color.DarkGray else Color.LightGray)
                    .clickable {
                        onUIEvent(GridUiEvent.OnGridItemClickedEvent(gridItem))
                    },
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }


    @Composable
    private fun BoxScope.TitleText() {
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier.align(Alignment.Center)
        )
    }


    @Composable
    private fun BoxScope.MergeButton(onUIEvent: (event: GridUiEvent) -> Unit, canMerge: Boolean) {
        Button(
            enabled = canMerge,
            modifier = Modifier
                .height(52.dp)
                .align(Alignment.CenterEnd),
            onClick = {
                onUIEvent(GridUiEvent.OnMergeButtonClickedEvent)
            }) {
            Text(
                text = stringResource(id = R.string.merge),
                color = if (canMerge) Color.Black else Color.LightGray
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        GridAppTheme {
            ShowGrid(GridViewModel())
        }
    }
}