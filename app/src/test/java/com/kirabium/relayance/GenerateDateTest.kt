package com.kirabium.relayance

import com.kirabium.relayance.data.service.CustomerFakeApi.generateDate
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GenerateDateTest {

    private lateinit var previousLocale: Locale

    @Before
    fun setUp() {
        previousLocale = Locale.getDefault()
        Locale.setDefault(Locale.FRANCE)
    }

    @After
    fun tearDown() {
        Locale.setDefault(previousLocale)
    }

    @Test
    fun `generates a date with 12 months back`() {
        val now = Date()
        val calendar = Calendar.getInstance().apply {
            time = now
            add(Calendar.MONTH, -12)
        }
        val actualDate = generateDate(12)

        val actual = actualDate.toHumanDate()
        val expected = calendar.time.toHumanDate()

        assertEquals(expected, actual)
    }

}