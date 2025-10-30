package com.kirabium.relayance

import android.R.attr.name
import android.R.attr.phoneNumber
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IsNewCustomerTest {
    private lateinit var previousLocale: Locale
    private val oldCustomer = DummyData.customers[0]
    private val threeMonthsCustomer = DummyData.customers[2]
    private val newCustomer = DummyData.customers[3]

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