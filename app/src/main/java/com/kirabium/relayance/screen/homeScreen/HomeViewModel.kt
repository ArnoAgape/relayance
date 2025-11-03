package com.kirabium.relayance.screen.homeScreen

import androidx.lifecycle.ViewModel
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.ui.common.Event
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _events = Channel<Event>()
    val eventsFlow = _events.receiveAsFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

}