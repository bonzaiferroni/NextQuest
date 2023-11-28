package com.glowsoft.nextquest.data

import com.glowsoft.nextquest.model.Quest

object SampleData {

    val quests: List<Quest> by lazy {
        var id = 0
        var nextQuestId = 0
        listOf(
            Quest(id = ++id, name = "make coffee").apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "throw away used filter", nextQuestId = nextQuestId),
            Quest(id = ++id, name = "add coffee", nextQuestId = nextQuestId).apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "add filter", nextQuestId = nextQuestId),
            Quest(id = ++id, name = "get mail").apply {
                nextQuestId = this.id
            },
            Quest(id = ++id, name = "put on shoes", nextQuestId = nextQuestId),
            Quest(id = ++id, name = "get key", nextQuestId = nextQuestId),
        )
    }
}