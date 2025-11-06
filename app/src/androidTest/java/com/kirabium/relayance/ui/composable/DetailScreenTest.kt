package com.kirabium.relayance.ui.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kirabium.relayance.R
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.screen.detailScreen.DetailContent
import com.kirabium.relayance.ui.activity.FakeCustomers.generateDate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val customer =
        Customer(1,
            "Alice Wonderland",
            "alice@example.com",
            generateDate(12)
        )

    @Test
    fun displaysCorrectCustomerDetails_andHandlesBackClick() {
        val createdAtText = composeTestRule.activity.getString(
            R.string.created_at, customer.createdAt.toHumanDate()
        )

        val goBackContentDescription = composeTestRule.activity.getString(
            R.string.contentDescription_go_back
        )

        composeTestRule.setContent { DetailContent(customer = customer) }

        composeTestRule.onNodeWithText(customer.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(customer.email).assertIsDisplayed()
        composeTestRule.onNodeWithText(createdAtText)
        composeTestRule.onNodeWithContentDescription(goBackContentDescription).performClick()
    }
}