Feature: Search Name

  Scenario: Search Aveiro by Name and Country and check Cache
    When I navigate to "http://localhost:3000"
    And I check that "hits" value is greater than 0
    And I check that "misses" value is greater than 0
    And I check that "requests" value is greater than 0
    And I insert "Aveiro" in "name"
    And I insert "PT" in "country"
    And I press "search_by_name"
    Then I should see that result contains "Aveiro"
    And I press "refresh"
    Then I should see that cache has changed
    And I check that "hits" value is greater than 0
    And I check that "misses" value is greater than 1
    And I check that "requests" value is greater than 1
    And I press "search_by_name"
    And I press "refresh"
    Then I should see that cache has changed
    And I check that "hits" value is greater than 1
    And I check that "misses" value is greater than 1
    And I check that "requests" value is greater than 2
    Then I close browser

  Scenario: Search by Wrong ID
    When I navigate to "http://localhost:3000"
    And I insert "Aveiro" in "name"
    And I insert "ES" in "country"
    And I press "search_by_name"
    Then I should see an error message
    Then I close browser
