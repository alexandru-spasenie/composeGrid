package com.compose.gridapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.compose.gridapp.composeui.GridComposeView

class MainActivity : ComponentActivity() {

    private val gridViewModel: GridViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GridComposeView().ShowGrid(gridViewModel)
        }
    }
}