package com.glowsoft.nextquest.ui.quest

import androidx.lifecycle.ViewModel
import com.glowsoft.nextquest.model.Quest

class NextQuestModel(
) : ViewModel() {
}

data class NextQuestUiState(
    val quests: List<Quest> = emptyList(),
)