package com.glowsoft.nextquest.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.Instant

@Entity(
    tableName = "quest",
    foreignKeys = [
        ForeignKey(
            entity = Quest::class,
            parentColumns = ["id"],
            childColumns = ["nextQuestId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("name"), Index("nextQuestId")]
)
data class Quest(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "my quest",
    val description: String? = null,
    // val passCondition: String?,
    // val due: Instant?,
    // val repeat: Boolean = false,
    // val repeatInterval: Duration,
    // val duration: Duration,
    val nextQuestId: Int? = null,
    // val avoidance: Float,
    // val curiosity: Float,
    // val confidence: Float,
    // val sheetId: Int,
    // val storyId: Int,
)