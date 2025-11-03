package com.kirabium.relayance.screen.addScreen

import androidx.lifecycle.ViewModel
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.ui.common.Event
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class AddViewModel@Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _events = Channel<Event>()
    val eventsFlow = _events.receiveAsFlow()

    private val _uiState = MutableStateFlow<AddUiState>(AddUiState.Loading)
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    }
