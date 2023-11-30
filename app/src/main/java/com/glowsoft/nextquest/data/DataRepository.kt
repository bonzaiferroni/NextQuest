package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAllQuests(): Flow<List<Quest>>
    fun getQuestById(id: Int): Flow<Quest>
    fun getSubQuestsById(nextQuestId: Int): Flow<List<Quest>>
    fun getFinalQuests(): Flow<List<Quest>>
    suspend fun insertQuest(quest: Quest): Int
    suspend fun updateQuest(quest: Quest)
    suspend fun deleteQuestById(id: Int)
    suspend fun deleteAllQuests()
}