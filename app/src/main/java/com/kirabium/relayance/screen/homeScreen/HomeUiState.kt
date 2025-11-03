package com.kirabium.relayance.screen.homeScreen

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the possible UI states for displaying a list of Customers.
 *
 * This sealed class allows the UI layer to react to data loading and errors:
 * - [Loading]: Shown while customers are being retrieved.
 * - [Success]: Contains the list of successfully loaded customers.
 * - [Error]: Represents issues such as an empty feed or generic failures.
 *
 * Commonly used in the Home Feed or Post List screens via a corresponding ViewModel.
 */
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val customers: List<Customer>) : HomeUiState()
    sealed class Error : HomeUiState() {
        data class Empty(val message: String = "No customers found") : Error()
        data class Generic(val message: String = "Unknown error") : Error()
    }
}