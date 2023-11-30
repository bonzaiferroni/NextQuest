package com.glowsoft.nextquest

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bollwerks.eznav.model.DrawerLinkConfig
import com.bollwerks.eznav.model.EzConfig
import com.bollwerks.eznav.model.ScreenConfig
import com.glowsoft.nextquest.ui.home.HomeModel
import com.glowsoft.nextquest.ui.home.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bollwerks.eznav.model.ScaffoldConfig
import com.glowsoft.nextquest.data.AppDatabase
import com.glowsoft.nextquest.data.DaoRepository
import com.glowsoft.nextquest.ui.export.ExportModel
import com.glowsoft.nextquest.ui.export.ExportScreen
import com.glowsoft.nextquest.ui.quest.NextQuestScreen
import com.glowsoft.nextquest.ui.quest.QuestMapModel
import com.glowsoft.nextquest.ui.quest.QuestMapScreen

val appConfig = EzConfig(
    mainAppIcon = { painterResource(R.drawable.ic_launcher_foreground) },
    vmFactoryBuilder = { context ->
        val appDatabase = AppDatabase.getDatabase(context)
        val dataRepository = DaoRepository(appDatabase.questDao())
        viewModelFactory {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                HomeModel()
            }
            initializer {
                QuestMapModel(
                    savedStateHandle = createSavedStateHandle(),
                    dataRepository = dataRepository,
                )
            }
            initializer {
                ExportModel(
                    dataRepository = dataRepository,
                    filesDirPath = context.filesDir.path,
                )
            }
        }
    },
    screens = listOf(
        ScreenConfig(
            route = AppRoutes.Home,
            content = { _, _, vmFactory ->
                HomeScreen(viewModel = viewModel(factory = vmFactory))
            },
            drawerLink = DrawerLinkConfig(
                route = AppRoutes.Home,
                title = "Home",
                emoji = "🏠"
            ),
            scaffold = ScaffoldConfig(
                title = "Home"
            )
        ),
        ScreenConfig(
            route = AppRoutes.QuestMap,
            content = { navController, drawerState, vmFactory ->
                QuestMapScreen(
                    navController = navController,
                    drawerState = drawerState,
                    viewModel = viewModel(factory = vmFactory)
                )
            },
            drawerLink = DrawerLinkConfig(
                route = AppRoutes.QuestMap,
                title = "Map",
                emoji = "🌄"
            ),
            isDefaultRoute = true,
        ),
        ScreenConfig(
            route = AppRoutes.Export,
            content = { navController, drawerState, vmFactory ->
                ExportScreen(
                    navController = navController,
                    drawerState = drawerState,
                    viewModel = viewModel(factory = vmFactory)
                )
            },
            drawerLink = DrawerLinkConfig(
                route = AppRoutes.Export,
                title = "Export",
                emoji = "📤"
            ),
            scaffold = ScaffoldConfig(
                title = "Export Data"
            ),
        ),
        ScreenConfig(
            route = AppRoutes.NextQuest,
            content = { _, _, vmFactory ->
                NextQuestScreen(
                    viewModel = viewModel(factory = vmFactory)
                )
            },
            drawerLink = DrawerLinkConfig(
                route = AppRoutes.NextQuest,
                title = "Next",
                emoji = "🔜"
            ),
            scaffold = ScaffoldConfig(
                title = "Next Quest"
            ),
        )
    )
)