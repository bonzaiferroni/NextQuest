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

val appConfig = EzConfig(
    mainAppIcon = { painterResource(R.drawable.ic_launcher_foreground) },
    vmFactoryBuilder = { context ->
        // val appDatabase = AppDatabase.getDatabase(context)
        // val dataRepository = DaoRepository(appDatabase.neuronDao())
        viewModelFactory {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                // val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                HomeModel()
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
            isDefaultRoute = true,
        )
    )
)