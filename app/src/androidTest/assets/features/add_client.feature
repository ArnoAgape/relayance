Feature: Add a new customer
  In order to manage my clients efficiently
  As a user of the application
  I want to be able to add a new customer to the list

  @smoke
    @e2e
  Scenario Outline: Successfully adding a new customer
    Given I am on the customer list screen
    When I tap the "Add" button
    And I enter the customer's name "<name>"
    And I enter the customer's email "<email>"
    And I tap the "Save" button
    Then I should see a confirmation message "Customer successfully added"
    And the new customer "<name>" should appear in the customer list

    Examples:
      | name        | email               |
      | Alice David | alicedavid@mail.com |