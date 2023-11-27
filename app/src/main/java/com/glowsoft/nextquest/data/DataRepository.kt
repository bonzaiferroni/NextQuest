package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAllQuests(): Flow<List<Quest>>
    fun getQuestById(id: Int): Flow<Quest>
    fun getPreviousQuestsById(nextQuestId: Int): Flow<List<Quest>>
    fun getRootQuests(): Flow<List<Quest>>
    suspend fun insertQuest(quest: Quest): Int
    suspend fun updateQuest(quest: Quest)
    suspend fun deleteQuestById(id: Int)
}