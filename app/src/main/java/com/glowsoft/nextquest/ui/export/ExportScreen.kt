package com.glowsoft.nextquest.ui.export

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bollwerks.memoryghost.utils.ghostui.MoreMenu
import com.bollwerks.memoryghost.utils.ghostui.MoreMenuItem
import com.glowsoft.nextquest.data.SampleRepository
import com.glowsoft.nextquest.ui.theme.NextQuestTheme
import com.glowsoft.nextquest.utils.Paddings
import com.glowsoft.nextquest.utils.PreviewDark
import com.glowsoft.nextquest.utils.paddingSmall
import com.glowsoft.nextquest.utils.spacedBySmall

@Composable
fun ExportScreen(
    navController: NavController?,
    drawerState: DrawerState?,
    viewModel: ExportModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .paddingSmall(),
        verticalArrangement = Arrangement.spacedBySmall()
    ) {
        Text("Save current map")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBySmall()
        ) {
            OutlinedTextField(
                value = uiState.newFilename,
                onValueChange = viewModel::setNewFilename,
                label = { Text("New Filename") },
            )
            Button(
                onClick = { viewModel.exportMap(context, uiState.newFilename) },
            ) {
                Text("Save")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBySmall()
        ) {
            Switch(
                checked = uiState.overwrite,
                onCheckedChange = { viewModel.toggleOverwrite() },
            )
            Text("Overwrite existing map")
            Spacer(modifier = Modifier.weight(1f))
            MoreMenu(items = listOf(
                MoreMenuItem(
                    name = "Import sample data",
                    onClick = viewModel::importSampleData,
                ),
            ))
        }

        Spacer(Modifier.height(8.dp))

        Text("Saved maps")
        LazyColumn(
            verticalArrangement = Arrangement.spacedBySmall()
        ) {
            items(uiState.filenames) { filename ->
                SavedFileCard(
                    filename = filename,
                    onImport = { viewModel.importJson(context, filename) }
                )
            }
        }
    }
}

@Composable
fun SavedFileCard(
    filename: String,
    onImport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.small()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBySmall()
        ) {
            Text(filename)
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onImport,
            ) {
                Text("Load")
            }
            Button(
                modifier= Modifier.size(40.dp),
                onClick = onImport,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "More"
                )
            }
        }
    }
}

@PreviewDark
@Composable
fun ExportScreenPreview() {
    NextQuestTheme {
        Surface {
            ExportScreen(
                navController = null,
                drawerState = null,
                viewModel = ExportModel(SampleRepository())
            )
        }
    }
}

@PreviewDark
@Composable
fun SavedFileCardPreview() {
    NextQuestTheme {
        Surface {
            SavedFileCard(
                filename = "test",
                onImport = {}
            )
        }
    }
}