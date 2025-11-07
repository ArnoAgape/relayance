package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.FakeCustomers.generateDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CustomerFakeApi : CustomerApi {

    private val customers = MutableStateFlow(
        mutableListOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDate(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDate(6)),
            Customer(3, "Charlie Chocolate", "charlie@example.com", generateDate(3)),
            Customer(4, "Diana Dream", "diana@example.com", generateDate(1)),
            Customer(5, "Evan Escape", "evan@example.com", generateDate(0)),
        )
    )

    override fun getCustomersOrderByCreationDateDesc(): Flow<List<Customer>> = customers

    override suspend fun addCustomer(customer: Customer) {
        customers.value.add(0, customer)
    }

    override fun getCustomerById(customerId: Int): Flow<Customer?> =
        customers.map { list ->
            list.find { it.id == customerId }
        }
}