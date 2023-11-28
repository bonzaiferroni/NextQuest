package com.glowsoft.nextquest.ui.export

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glowsoft.nextquest.data.DataRepository
import com.glowsoft.nextquest.data.DataSnapshot
import com.glowsoft.nextquest.data.SampleData
import com.glowsoft.nextquest.data.SampleRepository
import com.glowsoft.nextquest.data.insertQuests
import com.glowsoft.nextquest.model.Quest
import com.glowsoft.nextquest.utils.jsonToObject
import com.glowsoft.nextquest.utils.loadJsonFromFile
import com.glowsoft.nextquest.utils.objectToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class ExportModel(
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExportUiState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()

    init {
        updateFilenames()
    }

    fun setNewFilename(newFilename: String) {
        state = state.copy(newFilename = newFilename)
    }

    fun exportMap(context: Context, filename: String) {
        val validFilename = if (filename.endsWith(".json")) filename else "$filename.json"
        viewModelScope.launch {
            val quests = dataRepository.getAllQuests().first()
            val snapshot = DataSnapshot(quests = quests)
            context.objectToFile(snapshot, validFilename)
            updateFilenames()
        }
    }

    fun importJson(context: Context, filename: String) {
        val json = context.loadJsonFromFile(filename)
        val snapshot = jsonToObject<DataSnapshot>(json)
        importMap(snapshot.quests)
    }

    fun toggleOverwrite() {
        state = state.copy(overwrite = !state.overwrite)
    }

    fun importSampleData() {
        importMap(SampleData.quests)
    }

    private fun importMap(quests: List<Quest>) {
        viewModelScope.launch {
            if (state.overwrite) {
                dataRepository.deleteAllQuests()
            }
            dataRepository.insertQuests(quests)
        }
    }

    private fun updateFilenames() {
        viewModelScope.launch {
            val path = "/snapshots"
            val filenames = File(path).listFiles()
                ?.map { it.name }
                ?.map { it.removeSuffix(".json") }
                ?: emptyList()
            state = state.copy(filenames = filenames)
        }
    }
}

data class ExportUiState(
    val newFilename: String = "snapshot.json",
    val overwrite: Boolean = true,
    val filenames: List<String> = emptyList(),
)