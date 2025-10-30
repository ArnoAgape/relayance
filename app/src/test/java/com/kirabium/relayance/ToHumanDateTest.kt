package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Locale

class ToHumanDateTest {

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
    fun `converts the date to ddMMyyyy`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, 0, 1)
        }
        val date = calendar.time
        val dateToHumanDate = date.toHumanDate()

        assertEquals("01/01/2024", dateToHumanDate)
    }

}