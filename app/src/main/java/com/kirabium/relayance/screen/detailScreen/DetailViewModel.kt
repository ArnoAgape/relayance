package com.kirabium.relayance.screen.detailScreen

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = _events.receiveAsFlow()

    fun observeCustomer(customerId: Int) {
        viewModelScope.launch {
            dataRepository.getCustomerById(customerId)
                .onStart {
                    _uiState.update {
                        DetailUiState.Loading
                    }
                }
                .catch { e ->
                    _uiState.update {
                        DetailUiState.Error.Generic(e.message ?: "Something went wrong")
                    }
                    _events.trySend(Event.ShowMessage(R.string.error_generic))
                }
                .collect { customer ->
                    val newState = if (customer != null) {
                        DetailUiState.Success(customer)
                    } else {
                        _events.trySend(Event.ShowMessage(R.string.error_no_account_customer))
                        DetailUiState.Error.Empty("Impossible to find the customer")
                    }
                    _uiState.update { newState }
                }
        }
    }
}