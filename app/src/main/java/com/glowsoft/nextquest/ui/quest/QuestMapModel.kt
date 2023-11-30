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
                    val nextQuest = if (quest.superquestId != null) {
                        dataRepository.getQuestById(quest.superquestId).first()
                    } else {
                        null
                    }
                    state = state.copy(quest = quest, superquest = nextQuest)
                }
            }
            viewModelScope.launch {
                dataRepository.getSubQuestsById(questId).collect {
                    state = state.copy(subquests = it)
                }
            }
        } else {
            viewModelScope.launch {
                dataRepository.getFinalQuests().collect {
                    state = state.copy(finalQuests = it)
                }
            }
        }
    }

    fun toggleComplete() {
        state.quest?.let {
            viewModelScope.launch {
                val quest = it.copy(completedAt = it.completedAt)
                dataRepository.updateQuest(quest)
            }
        }
    }

    fun toggleNewQuestPopup() {
        state = state.copy(
            showNewQuestPopup = !state.showNewQuestPopup,
            newQuestName = "",
        )
    }

    fun onNewQuestNameChange(newQuestName: String) {
        state = state.copy(newQuestName = newQuestName)
    }

    fun createNewQuest() {
        val newQuestName = state.newQuestName
        if (newQuestName.isNotBlank()) {
            viewModelScope.launch {
                val superQuestId = if (questId == 0) null else questId
                val quest = Quest(name = newQuestName, superquestId = superQuestId)
                dataRepository.insertQuest(quest)
                state = state.copy(newQuestName = "", showNewQuestPopup = false)
            }
        }
    }
}

data class QuestMapUiState(
    val superquest: Quest? = null,
    val quest: Quest? = null,
    val subquests: List<Quest>? = null,
    val finalQuests: List<Quest>? = null,
    val newQuestName: String = "",
    val showNewQuestPopup: Boolean = false,
)