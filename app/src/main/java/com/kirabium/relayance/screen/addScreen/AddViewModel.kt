package com.kirabium.relayance.screen.addScreen

import androidx.lifecycle.ViewModel
import com.kirabium.relayance.R
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.IOException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _events = Channel<Event>(capacity = Channel.BUFFERED)
    val eventsFlow = _events.receiveAsFlow()

    private val _uiState = MutableStateFlow<AddUiState>(AddUiState.Idle)
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    private val _customer = MutableStateFlow(
        Customer(
            id = 0,
            name = "",
            email = "",
            createdAt = Date()
        )
    )

    /**
     * Public state flow representing the current customer being edited.
     * This is immutable for consumers.
     */
    val customer: StateFlow<Customer> = _customer.asStateFlow()

    private fun sendMessage(resId: Int) {
        _events.trySend(Event.ShowMessage(resId))
    }

    /**
     * Attempts to add the current post to the repository after setting the author.
     */
    suspend fun addCustomer(name: String, email: String) {
        val newName = name.trim()
        val newEmail = email.trim()

        when {
            newName.isBlank() -> {
                sendMessage(R.string.error_name)
                return
            }

            newEmail.isBlank() || !isEmailValid(newEmail) -> {
                sendMessage(R.string.error_email)
                return
            }
        }

        val customer = Customer(
            id = 0,
            name = newName,
            email = newEmail,
            createdAt = Date()
        )

        _uiState.value = AddUiState.Loading
        try {
            dataRepository.addCustomer(customer)

            _uiState.value = AddUiState.Success(customer)
            _events.trySend(Event.ShowSuccessMessage(R.string.add_customer_success))
            _events.trySend(Event.CustomerAdded)
            _uiState.value = AddUiState.Idle
        } catch (e: Exception) {
            when (e) {
                is IllegalStateException -> {
                    _uiState.value = AddUiState.Error.NoAccount()
                    sendMessage(R.string.error_no_account_customer)
                }

                is IOException -> {
                    _uiState.value = AddUiState.Error.Generic("Network error: ${e.message}")
                    sendMessage(R.string.error_no_network)
                }

                else -> {
                    _uiState.value = AddUiState.Error.Generic("Unexpected error: ${e.message}")
                    sendMessage(R.string.error_generic)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailRegex.matches(email)
    }
}
