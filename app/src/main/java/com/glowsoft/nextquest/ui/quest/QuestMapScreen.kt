package com.glowsoft.nextquest.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.glowsoft.phaser.elements.MoreMenu
import com.glowsoft.phaser.elements.MoreMenuItem
import com.glowsoft.nextquest.AppRoutes
import com.glowsoft.nextquest.RouteKeys
import com.glowsoft.nextquest.data.SampleRepository
import com.glowsoft.nextquest.model.Quest
import com.glowsoft.nextquest.ui.theme.NextQuestTheme
import com.glowsoft.phaser.PreviewDark
import com.glowsoft.phaser.elements.Popup
import com.glowsoft.phaser.elements.PopupButtons
import com.glowsoft.phaser.layout.Gaps
import com.glowsoft.phaser.layout.Paddings
import com.glowsoft.phaser.layout.spacedBySmall

@Composable
fun QuestMapScreen(
    drawerState: DrawerState,
    navController: NavController?,
    viewModel: QuestMapModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    NewQuestPopup(
        showNewQuestPopup = uiState.showNewQuestPopup,
        newQuestName = uiState.newQuestName,
        onNewQuestNameChange = viewModel::onNewQuestNameChange,
        createNewQuest = viewModel::createNewQuest,
        cancelNewQuest = viewModel::toggleNewQuestPopup,
    )

    EzScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = uiState.quest?.name ?: "Final Quests",
        fabConfig = FabConfig(
            icon = Icons.Filled.Add,
            onClick = viewModel::toggleNewQuestPopup,
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
            uiState.quest?.let { quest ->
                uiState.superquest?.let { superquest ->
                    OtherQuestCard(
                        quest = superquest,
                        onNavigate = {
                            AppRoutes.QuestMap.navigate(navController, superquest.id)
                        },
                        navIcon = Icons.Outlined.KeyboardArrowUp,
                        menuItems = emptyList(),
                    )
                } ?: run {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        QuestNavButton(
                            onClick = {
                                AppRoutes.QuestMap.navigate(navController, 0)
                            },
                            navIcon = Icons.Outlined.KeyboardArrowUp,
                        )
                    }
                }

                CurrentQuestCard(
                    quest = quest,
                    isReady = uiState.subquests?.all { subquest ->
                        subquest.completedAt != null
                    } ?: true,
                    onToggleComplete = viewModel::toggleComplete,
                    subquests = uiState.subquests,
                    navController = navController,
                )
            }

            // display previous quests or final quests
            uiState.finalQuests?.let { quests ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(quests) { quest ->
                        OtherQuestCard(
                            quest = quest,
                            onNavigate = {
                                AppRoutes.QuestMap.navigate(navController, quest.id)
                            },
                            navIcon = Icons.Outlined.KeyboardArrowDown,
                            menuItems = emptyList(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewQuestPopup(
    showNewQuestPopup: Boolean,
    newQuestName: String,
    onNewQuestNameChange: (String) -> Unit,
    createNewQuest: () -> Unit,
    cancelNewQuest: () -> Unit,
) {
    Popup(
        showDialog = showNewQuestPopup,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newQuestName,
            onValueChange = onNewQuestNameChange,
            label = { Text(text = "Quest name") },
        )
        PopupButtons(
            onAccept = createNewQuest,
            onCancel = cancelNewQuest,
        )
    }
}

@Composable
fun QuestRow(
    name: String,
    isReady: Boolean,
    isComplete: Boolean,
    onToggleComplete: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    menuItems: List<MoreMenuItem>? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isComplete,
            onCheckedChange = { onToggleComplete() },
            enabled = isReady,
        )
        Text(
            text = name,
            style = textStyle,
        )
        menuItems?.let { items ->
            Spacer(modifier = Modifier.weight(1f))
            MoreMenu(
                items = items,
            )
        }
    }
}

@Composable
fun CurrentQuestCard(
    quest: Quest,
    modifier: Modifier = Modifier,
    isReady: Boolean = false,
    onToggleComplete: () -> Unit,
    subquests: List<Quest>?,
    navController: NavController?,
) {
    if (quest.superquestId != null) {
        modifier.padding(start = Paddings.indent())
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = quest.completedAt != null,
                        onCheckedChange = { onToggleComplete() },
                        enabled = isReady,

                    )
                    Text(
                        text = quest.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(Paddings.small()),
                ) {
                    // hmm
                }
            }
        }

        subquests?.let { quests ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                modifier = Modifier
                    .padding(start = Paddings.indent())
                    .fillMaxWidth()
            ) {
                items(quests) { quest ->
                    OtherQuestCard(
                        quest = quest,
                        onNavigate = {
                            AppRoutes.QuestMap.navigate(navController, quest.id)
                        },
                        navIcon = Icons.Outlined.KeyboardArrowDown,
                        menuItems = emptyList(),
                    )
                }
            }
        }
    }
}

@Composable
fun OtherQuestCard(
    quest: Quest,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    navIcon: ImageVector,
    menuItems: List<MoreMenuItem>,
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
            QuestRow(
                name = quest.name,
                isComplete = quest.completedAt != null,
                isReady = false,
                onToggleComplete = {},
                menuItems = menuItems,
            )
        }
        QuestNavButton(
            onClick = onNavigate,
            navIcon = navIcon,
        )
    }
}

@Composable
fun QuestNavButton(
    onClick: () -> Unit,
    navIcon: ImageVector,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        Icon(
            imageVector = navIcon,
            contentDescription = null,
        )
    }
}

@PreviewDark
@Composable
fun QuestMapPreview() {
    NextQuestTheme {
        var savedStateHandle = SavedStateHandle()
        savedStateHandle[RouteKeys.id] = 2
        QuestMapScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            navController = null,
            viewModel = QuestMapModel(savedStateHandle, SampleRepository()),
        )
    }
}
