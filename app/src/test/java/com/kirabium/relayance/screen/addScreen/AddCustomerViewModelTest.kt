package com.kirabium.relayance.screen.addScreen

import app.cash.turbine.test
import com.kirabium.relayance.R
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.data.service.CustomerFakeApi
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.FakeCustomers.customers
import com.kirabium.relayance.ui.common.Event
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddCustomerViewModelTest {

    private lateinit var dataRepo: DataRepository
    private lateinit var viewModel: AddViewModel

    @Before
    fun setup() {
        dataRepo = mockk()
        viewModel = AddViewModel(dataRepo)
    }

    @Test
    fun isEmailValid() = runTest {
        // When
        viewModel.addCustomer("Alice David", "invalid_email")

        // Then
        viewModel.eventsFlow.test {
            val event = awaitItem()
            assertTrue(event is Event.ShowMessage)
            assertEquals(R.string.error_email, (event as Event.ShowMessage).message)
        }
    }

    @Test
    fun successMessage() = runTest {

        val fakeApi = CustomerFakeApi()
        val repository = DataRepository(fakeApi)
        val viewModel = AddViewModel(repository)

        // When
        viewModel.addCustomer("Alice David", "alice@mail.com")

        // Then
        viewModel.eventsFlow.test {
            val event = awaitItem()
            val event2 = awaitItem()

            assertTrue(event is Event.ShowSuccessMessage)
            assertTrue(event2 is Event.CustomerAdded)
            assertEquals(R.string.add_customer_success,(event as Event.ShowSuccessMessage).message)
        }
    }

    @Test
    fun addValidCustomer() = runTest {
        // Given
        val fakeApi = CustomerFakeApi()
        val repository = DataRepository(fakeApi)
        val viewModel = AddViewModel(repository)

        // When
        viewModel.addCustomer("Alice", "alice@mail.com")

        // Then
        fakeApi.getCustomersOrderByCreationDateDesc().test {
            val customers = awaitItem()

            assertEquals(6, customers.size)
            assertEquals("Alice", customers.first().name)
            assertEquals("alice@mail.com", customers.first().email)
        }
    }

}