package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest

data class DataSnapshot(
    val quests: List<Quest>,
)

suspend fun DataRepository.insertQuests(quests: List<Quest>) {
    quests
        .filter { it.nextQuestId == null }
        .forEach { insertQuestTree(it, quests)}
}

suspend fun DataRepository.insertQuestTree(quest: Quest, quests: List<Quest>) {
    val newId = this.insertQuest(quest.copy(id = 0))
    quests
        .filter { it.nextQuestId == quest.id }
        .forEach { insertQuestTree(it.copy(nextQuestId = newId), quests) }
}