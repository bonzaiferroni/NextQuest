package com.glowsoft.nextquest.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.glowsoft.nextquest.model.Quest
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quest")
    fun getAll(): Flow<List<Quest>>
}