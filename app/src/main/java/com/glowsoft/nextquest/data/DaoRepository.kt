package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.data.dao.QuestDao
import com.glowsoft.nextquest.model.Quest

class DaoRepository(
    private val questDao: QuestDao
) : DataRepository {
    fun getAllQuests() = questDao.getAll()
    fun getQuestById(id: Int) = questDao.getById(id)
    fun getQuestByName(name: String) = questDao.getByName(name)
    fun getQuestByNextQuestId(nextQuestId: Int) = questDao.getByNextQuestId(nextQuestId)
    suspend fun insertQuest(quest: Quest) = questDao.insert(quest)
    suspend fun insertAllQuests(vararg quests: Quest) = questDao.insertAll(*quests)
    suspend fun updateQuest(quest: Quest) = questDao.update(quest)
    suspend fun deleteQuestById(id: Int) = questDao.deleteById(id)
}