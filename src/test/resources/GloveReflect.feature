Feature: Glove reflects agents used by other player
  Scenario: PlayerOne gets protected by glove, agent is used on PlayerTwo
    Given there are two players on the same field
    Given a glove is equipped on PlayerOne
    Given PlayerTwo has virus with amnesia effect
    When PlayerTwo uses agent on PlayerOne
    Then PlayerOne is not infected
    Then PlayerTwo is infected
    Then agent is used up