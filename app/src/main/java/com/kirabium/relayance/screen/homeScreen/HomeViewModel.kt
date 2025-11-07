package com.kirabium.relayance.screen.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.R
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _events = Channel<Event>()
    val eventsFlow = _events.receiveAsFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllCustomers()
    }

    fun getAllCustomers() {
        viewModelScope.launch {
            dataRepository.customers
                .onStart { _uiState.value = HomeUiState.Loading }
                .catch { e ->
                    _uiState.value = HomeUiState.Error.Generic(e.message ?: "Unknown error")
                    _events.trySend(Event.ShowMessage(R.string.error_generic))
                }
                .collect { customers ->
                    if (customers.isEmpty()) {
                        _uiState.value = HomeUiState.Error.Empty("No customers found")
                        _events.trySend(Event.ShowMessage(R.string.error_no_account_customer))
                    } else {
                        _uiState.value = HomeUiState.Success(customers)
                    }
                }
        }
    }

}