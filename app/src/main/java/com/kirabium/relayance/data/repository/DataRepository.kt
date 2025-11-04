package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val customerApi: CustomerApi) {

    /**
     * Retrieves a Flow object containing a list of Customers ordered by creation date
     * in descending order.
     *
     * @return Flow containing a list of Customers.
     */
    val customers: Flow<List<Customer>> = customerApi.getCustomersOrderByCreationDateDesc()

    /**
     * Adds a new Customer to the data source using the injected CustomerApi.
     *
     * @param customer The Customer object to be added.
     */
    fun addCustomer(customer: Customer): Unit = customerApi.addCustomer(customer)

    /**
     * Collects a customer by its Id using the injected CustomerApi.
     *
     * @param customerId The Id of the customer to be collected.
     * @return Flow containing the collected Customer.
     */
    fun getCustomerById(customerId: Int): Flow<Customer?> = customerApi.getCustomerById(customerId)
}