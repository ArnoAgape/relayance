package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.FakeCustomers.generateDate
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IsNewCustomerTest {
    private val oldCustomer =
        Customer(1,
            "Alice Wonderland",
            "alice@example.com",
            generateDate(12)
        )
    private val threeMonthsCustomer =
        Customer(3,
            "Charlie Chocolate",
            "charlie@example.com",
            generateDate(3)
        )
    private val newCustomer =
        Customer(4,
            "Diana Dream",
            "diana@example.com",
            generateDate(1)
        )

    @Test
    fun `emits true when customer is less than three months old`() {
        assertTrue(newCustomer.isNewCustomer())
    }

    @Test
    fun `emits false when customer is exactly three months old`() {
        assertFalse(threeMonthsCustomer.isNewCustomer())
    }

    @Test
    fun `emits false when customer is more than three months old`() {
        assertFalse(oldCustomer.isNewCustomer())
    }
}