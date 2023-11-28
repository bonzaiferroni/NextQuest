package com.glowsoft.nextquest.ui.quest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glowsoft.nextquest.AppRoutes
import com.glowsoft.nextquest.data.DataRepository
import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuestMapModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val questId: Int = AppRoutes.QuestMap.getId(savedStateHandle = savedStateHandle) ?: 0

    private val _uiState = MutableStateFlow(QuestMapUiState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()

    init {
        if (questId != 0) {
            viewModelScope.launch {
                dataRepository.getQuestById(questId).collect { quest ->
                    val nextQuest = if (quest.nextQuestId != null) {
                        dataRepository.getQuestById(quest.nextQuestId).first()
                    } else {
                        null
                    }
                    state = state.copy(quest = quest, nextQuest = nextQuest)
                }
            }
            viewModelScope.launch {
                dataRepository.getPreviousQuestsById(questId).collect {
                    state = state.copy(previousQuests = it)
                }
            }
        } else {
            viewModelScope.launch {
                dataRepository.getFinalQuests().collect {
                    state = state.copy(previousQuests = it)
                }
            }
        }
    }

    fun toggleComplete(isComplete: Boolean) {
        state.quest?.let {
            viewModelScope.launch {
                val quest = it.copy(isComplete = isComplete)
                dataRepository.updateQuest(quest)
            }
        }
    }
}

data class QuestMapUiState(
    val nextQuest: Quest? = null,
    val quest: Quest? = null,
    val previousQuests: List<Quest>? = null,
)