package com.kirabium.relayance.screen.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    savedStateHandle: SavedStateHandle,
) :
    ViewModel() {

    private val customerId: Int = checkNotNull(savedStateHandle["customerId"])

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        observeCustomer()
    }

    private fun observeCustomer() {
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
                }
                .collect { customer ->
                    val newState = if (customer != null) {
                        DetailUiState.Success(customer)
                    } else {
                        DetailUiState.Error.Empty("Impossible to find the customer")
                    }
                    _uiState.update { newState }
                }
        }
    }
}