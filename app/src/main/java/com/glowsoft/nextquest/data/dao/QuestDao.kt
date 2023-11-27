package com.glowsoft.nextquest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quest")
    fun getAll(): Flow<List<Quest>>

    @Query("SELECT * FROM quest WHERE id = :id")
    fun getById(id: Int): Flow<Quest>

    @Query("SELECT * FROM quest WHERE name LIKE :name")
    fun getByName(name: String): Flow<List<Quest>>

    @Query("SELECT * FROM quest WHERE nextQuestId = :nextQuestId")
    fun getByNextQuestId(nextQuestId: Int): Flow<List<Quest>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quest: Quest)

    @Insert
    suspend fun insertAll(vararg quests: Quest)

    @Update
    suspend fun update(quest: Quest)

    @Query("DELETE FROM quest WHERE id = :id")
    suspend fun deleteById(id: Int)
}