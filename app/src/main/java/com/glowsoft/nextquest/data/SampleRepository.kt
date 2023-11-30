package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleRepository : DataRepository {
    override fun getAllQuests(): Flow<List<Quest>> {
        return flowOf(SampleData.quests)
    }

    override fun getQuestById(id: Int): Flow<Quest> {
        return flowOf(SampleData.quests.first { it.id == id })
    }

    override fun getSubQuestsById(nextQuestId: Int): Flow<List<Quest>> {
        return flowOf(SampleData.quests.filter { it.superquestId == nextQuestId })
    }

    override fun getFinalQuests(): Flow<List<Quest>> {
        return flowOf(SampleData.quests.filter { it.superquestId == null })
    }

    override suspend fun insertQuest(quest: Quest): Int {
        return 0
    }

    override suspend fun updateQuest(quest: Quest) {
        // no-op
    }

    override suspend fun deleteQuestById(id: Int) {
        // no-op
    }

    override suspend fun deleteAllQuests() {
        // no-op
    }
}