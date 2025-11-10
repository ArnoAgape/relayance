package com.kirabium.relayance.test

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.MainActivity

class AddScreenRobot {

    fun launchHomeScreen() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    fun tapAddButton() {
        onView(withId(R.id.addCustomerFab))
            .perform(click())
    }

    fun enterName(name: String) {
        onView(withId(R.id.nameEditText))
            .perform(typeText(name), closeSoftKeyboard())
    }

    fun enterEmail(email: String) {
        onView(withId(R.id.emailEditText))
            .perform(typeText(email), closeSoftKeyboard())
    }

    fun tapSaveButton() {
        onView(withId(R.id.saveFab))
            .perform(click())
    }

    fun isSuccessfullyAdded(message: String) {
        onView(withText(message))
            .check(matches(isDisplayed()))
    }

    fun verifyCustomer(name: String) {
        onView(withText(name))
            .check(matches(isDisplayed()))
    }

}