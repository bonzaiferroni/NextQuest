package com.glowsoft.nextquest.ui.quest

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun QuestMapScreen(
    viewModel: QuestMapModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Text("hello quest")
}