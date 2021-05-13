Feature: Search Id

  Scenario: Search Aveiro by ID and check Cache
    When I navigate to "http://localhost:3000"
    And I check that "hits" value is equal to 0
    And I check that "misses" value is equal to 0
    And I check that "requests" value is equal to 0
    And I insert "2742611" in "city_id"
    And I press "search_by_id"
    Then I should see that result contains "Aveiro"
    And I press "refresh"
    Then I should see that cache has changed
    And I check that "hits" value is equal to 0
    And I check that "misses" value is equal to 1
    And I check that "requests" value is equal to 1
    And I press "search_by_id"
    And I press "refresh"
    Then I should see that cache has changed
    And I check that "hits" value is equal to 1
    And I check that "misses" value is equal to 1
    And I check that "requests" value is equal to 2

  Scenario: Search by Wrong ID
    When I navigate to "http://localhost:3000"
    And I insert "1" in "city_id"
    And I press "search_by_id"
    Then I should see an error message