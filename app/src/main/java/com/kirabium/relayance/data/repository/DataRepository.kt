package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(private val customerApi: CustomerApi) {

    /**
     * Retrieves a Flow object containing a list of Customers ordered by creation date
     * in descending order.
     *
     * @return Flow containing a list of Customers.
     */
    val customers: Flow<List<Customer>> = customerApi.getCustomersOrderByCreationDateDesc()

    suspend fun addCustomer(customer: Customer): Unit = customerApi.addCustomer(customer)

    fun getCustomerById(customerId: Int): Flow<Customer?> = customerApi.get
}