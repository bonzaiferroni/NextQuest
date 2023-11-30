package com.glowsoft.nextquest

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.bollwerks.eznav.EzRoute
import com.bollwerks.eznav.model.RouteParam

object AppRoutes {
    object Home : EzRoute("home")
    object QuestMap : EzRoute(
        "quest_map",
        RouteParam(RouteKeys.id, NavType.IntType, true, 0)
    ) {
        fun navigate(navController: NavController?, id: Int? = null) {
            navigate(navController, RouteKeys.id to id)
        }

        fun getId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle[RouteKeys.id]
        }
    }

    object Export : EzRoute("export")
    object NextQuest : EzRoute("next_quest")
}

object RouteKeys {
    const val id = "id"
    // const val nextQuestId = "nextQuestId"
    // const val previousQuestId = "previousQuestId"
}