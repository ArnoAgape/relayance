package com.kirabium.relayance.screen.addScreen

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the different UI states for the Add Customer screen.
 *
 * This sealed class models the lifecycle of adding a customer, including:
 * - [Idle]: The default state before any user action.
 * - [Loading]: The state shown while the customer is being uploaded or processed.
 * - [Success]: Indicates that the customer was successfully created.
 * - [Error]: Represents failure cases such as missing account or unknown errors.
 *
 * Used by [AddViewModel] to drive the UI reactively through state changes.
 */
sealed class AddUiState {
    object Idle : AddUiState()
    object Loading : AddUiState()
    data class Success(val customer: Customer) : AddUiState()
    sealed class Error : AddUiState() {
        data class NoAccount(val message: String = "No customer account found") : Error()
        data class Generic(val message: String = "Unknown error") : Error()
    }
}