package com.glowsoft.nextquest.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(viewModel: HomeModel) {
    val uiState by viewModel.uiState.collectAsState()
    Text(text = uiState.greeting)
}