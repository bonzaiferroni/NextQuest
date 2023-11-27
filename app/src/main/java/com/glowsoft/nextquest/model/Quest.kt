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
    val name: String,
    val description: String,
    val passCondition: String,
    val due: Instant?,
    val repeat: Boolean,
    val repeatInterval: Duration,
    val duration: Duration,
    val nextQuestId: Int,
    // val avoidance: Float,
    // val curiosity: Float,
    // val confidence: Float,
    // val sheetId: Int,
    // val storyId: Int,
)