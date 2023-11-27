package com.glowsoft.nextquest.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.bollwerks.eznav.utils.Gaps
import com.bollwerks.eznav.utils.Paddings

@Composable
fun QuestMapScreen(
    drawerState: DrawerState,
    navController: NavController?,
    viewModel: QuestMapModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    EzScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "🌄",
        fabConfig = FabConfig(
            icon = Icons.Filled.Add,
            onClick = {
                // viewModel::addQuest
            },
            contentDescription = "Add quest",
        ),
        menuItems = listOf(
            ScreenMenuItem(
                name = "Export",
                onClick = {
                    // viewModel.exportTree(context)
                },
            ),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.small()),
            verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
        ) {
            Text("hello quest")
        }
    }
}