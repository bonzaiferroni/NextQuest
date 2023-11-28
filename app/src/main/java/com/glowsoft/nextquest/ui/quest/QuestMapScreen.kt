package com.glowsoft.nextquest.ui.quest

import android.content.res.Configuration
import android.widget.CheckBox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.bollwerks.eznav.utils.Gaps
import com.bollwerks.eznav.utils.Paddings
import com.bollwerks.eznav.utils.PreviewDark
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
                OtherQuestCard(it.name) { }
            }

            // display previous quests or final quests
            uiState.previousQuests?.let { previousQuests ->
                Spacer(modifier = Modifier.height(Gaps.large()))
                Text(text = "Previous quests")
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
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isComplete,
                onCheckedChange = { }
            )
            Text(text = name)
        }
    }
}

@Composable
fun CurrentQuestCard(
    name: String,
    modifier: Modifier = Modifier,
    isComplete: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isComplete,
            onCheckedChange = { }
        )
        Text(text = name)
    }
}

@PreviewDark
@Composable
fun QuestMapPreview() {
    NextQuestTheme {
        var savedStateHandle = SavedStateHandle()
        savedStateHandle[RouteKeys.id] = 1
        QuestMapScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            navController = null,
            viewModel = QuestMapModel(savedStateHandle, SampleRepository()),
        )
    }
}
