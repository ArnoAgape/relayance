package com.kirabium.relayance.screen.detailScreen

import app.cash.turbine.test
import com.kirabium.relayance.data.repository.DataRepository
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.FakeCustomers.generateDate
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var dataRepo: DataRepository
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        dataRepo = mockk()
        viewModel = DetailViewModel(dataRepo)
    }

    @Test
    fun `uiState emits Success when customer is loaded`() = runTest {
        // Arrange
        val fakeCustomer = Customer(0,
            "Alice David",
            "alicedavid@mail.com",
            generateDate(12))

        coEvery { dataRepo.getCustomerById(fakeCustomer.id) } returns flow { emit(fakeCustomer) }

        // Act
        viewModel = DetailViewModel(dataRepo)
        viewModel.observeCustomer(0)

        // Assert
        viewModel.uiState.test {
            val successState = awaitItem()

            assertTrue(successState is DetailUiState.Success)
            assertEquals(fakeCustomer, (successState as DetailUiState.Success).customer)
        }
    }

    @Test
    fun `uiState emits Error_Generic when repository throws exception`() = runTest {
        // Arrange
        val fakeCustomer = Customer(0,
            "Alice David",
            "alicedavid@mail.com",
            generateDate(12))

        coEvery { dataRepo.getCustomerById(fakeCustomer.id) } returns flow { throw Exception() }

        // Act
        viewModel.observeCustomer(0)

        // Assert
        viewModel.uiState.test {
            val error = awaitItem()
            assertTrue(error is DetailUiState.Error.Generic)
        }
    }

    @Test
    fun `uiState emits Error_Empty when customer list is empty`() = runTest {
        // Arrange
        val fakeCustomerId = 0

        coEvery { dataRepo.getCustomerById(fakeCustomerId) } returns flow { emit(null) }

        // Act
        viewModel.observeCustomer(fakeCustomerId)

        // Assert
        viewModel.uiState.test {
            val emptyState = awaitItem()
            assertTrue(emptyState is DetailUiState.Error.Empty)
        }
    }

}