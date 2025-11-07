package com.kirabium.relayance.test

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class AddCustomerSteps {

    private val robot = AddScreenRobot()

    @Given("I am on the customer list screen")
    fun iAmOnCustomerList() {
        robot.launchHomeScreen()
    }

    @When("I tap the {string} button")
    fun iTapAddButton(buttonName: String) {
        robot.tapAddButton()
    }

    @And("I click the customer's name field")
    fun iClickName() {
        robot.selectNameField()
    }

    @And("I enter the customer's name {string}")
    fun iEnterNonEmptyName(name: String) {
        robot.enterName(name)
    }

    @And("I click the customer's email field")
    fun iClickEmail() {
        robot.selectEmailField()
    }

    @And("I enter the customer's email {string}")
    fun iEnterEmail(email: String) {
        robot.enterEmail(email)
    }

    @And("I tap the {string} button on the add screen")
    fun iTapSaveButton(buttonName: String) {
        robot.tapSaveButton()
    }

    @Then("I should see a confirmation message {string}")
    fun iSeeMessage(expectedMessage: String) {
        robot.isSuccessfullyAdded(expectedMessage)
    }

    @And("the new customer {string} should appear in the customer list")
    fun theNewCustomerShouldAppear(customerName: String) {
        robot.verifyCustomer(customerName)
    }
}