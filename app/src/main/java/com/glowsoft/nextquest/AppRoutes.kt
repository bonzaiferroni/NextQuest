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
    object EditQuest : EzRoute(
        "edit_quest",
        RouteParam(RouteKeys.id, NavType.IntType, true, 0),
        RouteParam(RouteKeys.nextQuestId, NavType.IntType, true, 0),
        RouteParam(RouteKeys.previousQuestId, NavType.IntType, true, 0),
    ) {
        fun navigate(
            navController: NavController?,
            id: Int? = null,
            nextQuestId: Int? = null,
            previousQuestId: Int? = null,
        ) {
            navigate(navController,
                RouteKeys.id to id,
                RouteKeys.nextQuestId to nextQuestId,
                RouteKeys.previousQuestId to previousQuestId)
        }

        fun getId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle[RouteKeys.id]
        }

        fun getNextQuestId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle[RouteKeys.nextQuestId]
        }

        fun getPreviousQuestId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle[RouteKeys.previousQuestId]
        }
    }
}

object RouteKeys {
    const val id = "id"
    const val nextQuestId = "nextQuestId"
    const val previousQuestId = "previousQuestId"
}