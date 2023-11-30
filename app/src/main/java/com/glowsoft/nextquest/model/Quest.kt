package com.glowsoft.nextquest.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quest",
    foreignKeys = [
        ForeignKey(
            entity = Quest::class,
            parentColumns = ["id"],
            childColumns = ["superQuestId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("name"), Index("superQuestId")]
)
data class Quest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "my quest",
    val description: String? = null,
    val isComplete: Boolean = false,
    // val passCondition: String?,
    // val due: Instant?,
    // val repeat: Boolean = false,
    // val repeatInterval: Duration,
    // val duration: Duration,
    val superQuestId: Int? = null,
    // val avoidance: Float,
    // val curiosity: Float,
    // val confidence: Float,
    // val sheetId: Int,
    // val storyId: Int,
)