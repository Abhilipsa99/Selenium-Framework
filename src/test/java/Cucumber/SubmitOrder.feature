Feature: Purchase the order from Ecommerce Website

  Background:
    Given I landed on Ecommerce page

  @SubmitOrder
  Scenario Outline: Positive test for submitting the order
    Given Logged in with userName <userName> and password <password>
    When I add the product <productName> to cart
    And checkout <productName> and submit the order
    Then verify "THANKYOU FOR THE ORDER." message is displayed on confirmation page
    Examples:
      | userName                | password    | productName   |
      | abhilipsamail@gmail.com | Aradhya@421 | IPHONE 13 PRO |