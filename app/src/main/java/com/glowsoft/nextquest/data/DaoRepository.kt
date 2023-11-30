package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.data.dao.QuestDao
import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow

class DaoRepository(
    private val questDao: QuestDao
) : DataRepository {
    override fun getAllQuests(): Flow<List<Quest>> = questDao.getAll()
    override fun getQuestById(id: Int) = questDao.getById(id)
    override fun getSubQuestsById(nextQuestId: Int) = questDao.getPreviousQuests(nextQuestId)
    override fun getFinalQuests(): Flow<List<Quest>> = questDao.getFinalQuests()
    override suspend fun insertQuest(quest: Quest) = questDao.insert(quest).toInt()
    override suspend fun updateQuest(quest: Quest) = questDao.update(quest)
    override suspend fun deleteQuestById(id: Int) = questDao.deleteById(id)
    override suspend fun deleteAllQuests() = questDao.deleteAll()
}