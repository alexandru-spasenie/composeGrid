package com.compose.gridapp.models

data class GridModel(val gridItemList: MutableList<GridItem> = mutableListOf(), val canMergeItems: Boolean = false)

data class GridItem(val name: String = "", var selected: Boolean = false)