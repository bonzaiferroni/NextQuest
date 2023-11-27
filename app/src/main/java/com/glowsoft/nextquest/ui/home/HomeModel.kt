package com.glowsoft.nextquest.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    private var state: HomeUiState
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()

}

data class HomeUiState(
    val greeting: String = "Hello EzNav!"
)