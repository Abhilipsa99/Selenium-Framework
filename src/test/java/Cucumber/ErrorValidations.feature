Feature: Error Validations

  @ErrorValidations
  Scenario Outline: Validate the login feature with invalid credentials
    Given I landed on Ecommerce page
    When Logged in with userName <userName> and password <password>
    Then verify "Incorrect email or password." is displayed
    Examples:
      | userName                | password      |
      | abhilipsamail@gmail.com | Aradhyaa@4211 |