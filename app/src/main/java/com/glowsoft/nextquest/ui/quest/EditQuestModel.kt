package com.glowsoft.nextquest.ui.quest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.glowsoft.nextquest.AppRoutes
import com.glowsoft.nextquest.data.DataRepository
import com.glowsoft.nextquest.model.Quest

class EditQuestModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {
    private val questId: Int =
        AppRoutes.EditQuest.getId(savedStateHandle = savedStateHandle) ?: 0
    private val nextQuestId: Int =
        AppRoutes.EditQuest.getNextQuestId(savedStateHandle = savedStateHandle) ?: 0
    private val previousQuestId: Int =
        AppRoutes.EditQuest.getPreviousQuestId(savedStateHandle = savedStateHandle) ?: 0
}

data class EditQuestUiState(
    val quest: Quest? = null,
)