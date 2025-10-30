package com.kirabium.relayance.ui.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kirabium.relayance.R
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.DetailActivity
import com.kirabium.relayance.ui.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val customer = DummyData.customers[0]

    @Test
    fun displaysCorrectCustomerDetails_andHandlesBackClick() {
        val createdAtText = composeTestRule.activity.getString(
            R.string.created_at, customer.createdAt.toHumanDate()
        )

        val goBackContentDescription = composeTestRule.activity.getString(
            R.string.contentDescription_go_back
        )

        composeTestRule.setContent {DetailScreen(customer = customer) { }}

        composeTestRule.onNodeWithText(customer.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(customer.email).assertIsDisplayed()
        composeTestRule.onNodeWithText(createdAtText)
        composeTestRule.onNodeWithContentDescription(goBackContentDescription).performClick()
    }
}