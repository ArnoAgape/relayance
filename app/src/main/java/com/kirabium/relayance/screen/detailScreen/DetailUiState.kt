package com.kirabium.relayance.screen.detailScreen

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the different UI states for displaying detailed information about a Customer.
 *
 * This sealed class is used to manage and communicate the loading, success, and
 * error states when retrieving customer details:
 * - [Loading]: Indicates data is currently being fetched.
 * - [Success]: Contains the successfully retrieved [Customer].
 * - [Error]: Represents failure scenarios, such as missing or unavailable customers.
 *
 * Typically observed from [DetailViewModel] to update the corresponding UI.
 */
sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val customer: Customer) : DetailUiState()
    sealed class Error : DetailUiState() {
        data class Empty(val message: String = "No customers found") : Error()
        data class Generic(val message: String = "Unknown error") : Error()
    }
}