package com.glowsoft.nextquest.ui.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.bollwerks.eznav.utils.Gaps
import com.bollwerks.eznav.utils.Paddings
import com.glowsoft.nextquest.AppRoutes

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
            uiState.nextQuest?.let { nextQuest ->
                OtherQuestCard(
                    name = nextQuest.name,
                    onClick = {
                        AppRoutes.QuestMap.navigate(navController, nextQuest.id)
                    }
                )
            } ?: run {
                if (uiState.quest != null) {
                    OtherQuestCard(
                        name = "🗺",
                        onClick = {
                            AppRoutes.QuestMap.navigate(navController)
                        }
                    )
                }
            }

            uiState.quest?.let {
                OtherQuestCard(it.name) { }
            }

            // display previous quests
            uiState.previousQuests?.let { previousQuests ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(previousQuests) { quest ->
                        OtherQuestCard(
                            name = quest.name,
                            onClick = {
                                AppRoutes.QuestMap.navigate(navController, quest.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OtherQuestCard(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable { onClick() },
    ) {
        Row(modifier = Modifier.padding(Paddings.small())) {
            Text(text = name)
        }
    }
}

@Composable
fun CurrentQuestCard() {
    Card() {
    }
}
