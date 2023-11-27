package com.glowsoft.nextquest.ui.quest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.glowsoft.nextquest.data.DataRepository
import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestMapModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestMapUiState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()


}

data class QuestMapUiState(
    val quests: List<Quest> = emptyList(),
)