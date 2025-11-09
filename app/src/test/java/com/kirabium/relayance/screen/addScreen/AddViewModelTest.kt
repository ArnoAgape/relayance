package com.kirabium.relayance.screen.addScreen

import app.cash.turbine.test
import com.kirabium.relayance.MainDispatcherRule
import com.kirabium.relayance.R
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.data.service.CustomerFakeApi
import com.kirabium.relayance.ui.common.Event
import com.kirabium.relayance.ui.utils.AndroidEmailValidator
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dataRepo: DataRepository
    private lateinit var viewModel: AddViewModel
    private lateinit var emailValidator: AndroidEmailValidator

    @Before
    fun setup() {
        emailValidator = mockk()
        dataRepo = mockk()
        viewModel = AddViewModel(dataRepo, emailValidator)
    }

    @Test
    fun isEmailValid() = runTest {
        // When
        every { emailValidator.validate(any()) } returns false

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

        every { emailValidator.validate(any()) } returns true
        val viewModel = AddViewModel(repository, emailValidator)

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
        every { emailValidator.validate(any()) } returns true
        val viewModel = AddViewModel(repository, emailValidator)

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