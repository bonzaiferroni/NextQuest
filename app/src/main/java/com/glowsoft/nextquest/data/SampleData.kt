package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest
import java.time.Instant

object SampleData {

    val quests: List<Quest> by lazy {
        var id = 0
        var nextQuestId = 0
        listOf(
            Quest(id = ++id, name = "make coffee").apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "throw away used filter", superquestId = nextQuestId,
                completedAt = Instant.now()),
            Quest(id = ++id, name = "add coffee", superquestId = nextQuestId).apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "add filter", superquestId = nextQuestId),
            Quest(id = ++id, name = "get mail").apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "put on shoes", superquestId = nextQuestId),
            Quest(id = ++id, name = "get key", superquestId = nextQuestId),
        )
    }
}