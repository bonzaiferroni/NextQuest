package com.glowsoft.nextquest.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.glowsoft.nextquest.utils.Gaps
import com.glowsoft.nextquest.utils.Paddings
import com.glowsoft.nextquest.utils.PreviewDark
import com.glowsoft.nextquest.utils.spacedBySmall
import com.glowsoft.nextquest.AppRoutes
import com.glowsoft.nextquest.RouteKeys
import com.glowsoft.nextquest.data.SampleRepository
import com.glowsoft.nextquest.ui.theme.NextQuestTheme

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
            uiState.quest?.let {
                uiState.nextQuest?.let { nextQuest ->
                    Text(text = "Next quest")
                    OtherQuestCard(
                        name = nextQuest.name,
                        onClick = {
                            AppRoutes.QuestMap.navigate(navController, nextQuest.id)
                        }
                    )
                } ?: run {
                    MapButton()
                }

                Spacer(modifier = Modifier.height(Gaps.large()))
                Text(text = "Current quest")
                CurrentQuestCard(
                    name = it.name,
                    isComplete = it.isComplete,
                    isReady = uiState.previousQuests?.all { quest -> quest.isComplete } ?: true,
                    onToggleComplete = viewModel::toggleComplete,
                )
                Spacer(modifier = Modifier.height(Gaps.large()))
            }

            // display previous quests or final quests
            uiState.previousQuests?.let { previousQuests ->
                val header = if (uiState.quest != null) {
                    "Previous quests"
                } else {
                    "Final quests"
                }
                Text(text = header)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(previousQuests) { quest ->
                        OtherQuestCard(
                            name = quest.name,
                            isComplete = quest.isComplete,
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
fun MapButton(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    Button(
        modifier = modifier,
        onClick = {
            AppRoutes.QuestMap.navigate(navController)
        }
    ) {
        Text(text = "Map", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun OtherQuestCard(
    name: String,
    modifier: Modifier = Modifier,
    isComplete: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBySmall()
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = isComplete,
                    onCheckedChange = { },
                    enabled = false,
                )
                Text(text = name)
            }
        }
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(all = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun CurrentQuestCard(
    name: String,
    modifier: Modifier = Modifier,
    isComplete: Boolean = false,
    isReady: Boolean = false,
    onToggleComplete: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isComplete,
                onCheckedChange = onToggleComplete,
                enabled = isReady,
            )
            Text(text = name)
        }
    }
}

@PreviewDark
@Composable
fun QuestMapPreview() {
    NextQuestTheme {
        var savedStateHandle = SavedStateHandle()
        savedStateHandle[RouteKeys.id] = 0
        QuestMapScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            navController = null,
            viewModel = QuestMapModel(savedStateHandle, SampleRepository()),
        )
    }
}
