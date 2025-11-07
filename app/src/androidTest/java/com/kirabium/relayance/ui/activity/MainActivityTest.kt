package com.kirabium.relayance.ui.activity

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kirabium.relayance.R
import com.kirabium.relayance.utils.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displaysFiveItemsAtStartup() {
        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(5))
    }
}