package com.kirabium.relayance

import com.kirabium.relayance.data.service.CustomerFakeApi
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IsNewCustomerTest {
    private val oldCustomer = CustomerFakeApi.customers[0]
    private val threeMonthsCustomer = CustomerFakeApi.customers[2]
    private val newCustomer = CustomerFakeApi.customers[3]

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