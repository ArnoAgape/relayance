package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines the contract for interacting with Customer data from a data source.
 * It outlines the methods for retrieving and adding Customers, abstracting the underlying
 * implementation details of fetching and persisting data.
 */
interface CustomerApi {
    /**
     * Retrieves a list of Customers ordered by their creation date in descending order.
     *
     * @return A list of Customers sorted by creation date (newest first).
     */
    fun getCustomersOrderByCreationDateDesc(): Flow<List<Customer>>

    /**
     * Adds a new Customer to the data source.
     *
     * @param customer The Customer object to be added.
     */
    suspend fun addCustomer(customer: Customer)

    /**
     * Retrieves a [Flow] emitting a [Customer] matching the given [customerId].
     *
     * @param customerId The unique identifier of the post to retrieve.
     * @return A [Flow] emitting the matching [Customer] or `null` if not found.
     */
    fun getCustomerById(customerId: Int): Flow<Customer?>
}