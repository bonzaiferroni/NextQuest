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
    private val savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val questId: Int? = AppRoutes.QuestMap.getId(savedStateHandle = savedStateHandle)

    private val _uiState = MutableStateFlow(QuestMapUiState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()

    init {
        if (questId != null) {
            viewModelScope.launch {
                val quest = dataRepository.getQuestById(questId).first()
                if (quest.nextQuestId != null) {
                    val nextQuest = dataRepository.getQuestById(quest.nextQuestId).first()
                    state = state.copy(quest = nextQuest, nextQuest = nextQuest)
                } else {
                    state = state.copy(quest = quest)
                }
            }
            viewModelScope.launch {
                dataRepository.getPreviousQuestsById(questId).collect {
                    state = state.copy(previousQuests = it)
                }
            }
        } else {
            viewModelScope.launch {
                dataRepository.getRootQuests().collect {
                    state = state.copy(previousQuests = it)
                }
            }
        }
    }
}

data class QuestMapUiState(
    val nextQuest: Quest? = null,
    val quest: Quest? = null,
    val previousQuests: List<Quest>? = null,
)