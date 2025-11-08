package com.kirabium.relayance.screen.homeScreen

import app.cash.turbine.test
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.FakeCustomers.generateDate
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var fakeApi: CustomerFakeApi
    private lateinit var dataRepo: DataRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        fakeApi = CustomerFakeApi()
        dataRepo = DataRepository(fakeApi)
        viewModel = HomeViewModel(dataRepo)
    }

    @Test
    fun `uiState emits Success when customers are loaded`() = runTest {
        // Arrange
        val fakeCustomers = listOf(
            Customer(1, "Alice", "alicedavid@mail.com", generateDate(12)),
            Customer(2, "Bob", "bob@mail.com", generateDate(3))
        )

        fakeApi.setFakeCustomers(fakeCustomers)

        // Act
        viewModel.getAllCustomers()

        // Assert
        viewModel.uiState.test {
            val successState = awaitItem()

            assertTrue(successState is HomeUiState.Success)
            assertEquals(fakeCustomers, (successState as HomeUiState.Success).customers)
        }
    }

    @Test
    fun `uiState emits Error_Generic when repository throws exception`() = runTest {
        // Arrange
        val fakeApi = mockk<CustomerApi>()

        every { fakeApi.getCustomersOrderByCreationDateDesc() } returns flow { throw Exception() }

        val dataRepo = DataRepository(fakeApi)
        val viewModel = HomeViewModel(dataRepo)

        // Act
        viewModel.getAllCustomers()

        // Assert
        viewModel.uiState.test {
            val error = awaitItem()
            assertTrue(error is HomeUiState.Error.Generic)
        }
    }
}