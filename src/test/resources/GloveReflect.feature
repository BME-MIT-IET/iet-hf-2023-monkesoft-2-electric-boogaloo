Feature: Glove reflects agents used by other player
  Scenario: PlayerOne gets protected by glove, agent is used on PlayerTwo, PlayerTwo gets infected
    Given there are two players on the same field
    Given a glove is equipped on PlayerOne
    Given PlayerTwo has virus with confusion effect
    When PlayerTwo uses agent on PlayerOne
    Then PlayerOne is not infected
    Then PlayerTwo is infected
    Then agent is used up

  Scenario: PlayerOne gets protected by glove, and PlayerTwo also has a glove
    Given there are two players on the same field
    Given a glove is equipped on PlayerOne
    Given a glove is equipped on PlayerTwo
    Given PlayerTwo has virus with confusion effect
    When PlayerTwo uses agent on PlayerOne
    Then PlayerOne is not infected
    Then PlayerTwo is not infected
    Then agent is used up

  Scenario: PlayerOne gets protected by glove, and PlayerTwo is protected by a cape
    Given  there are two players on the same field
    Given a glove is equipped on PlayerOne
    Given a cape with 100% protection is equipped on PlayerTwo
    Given PlayerTwo has virus with confusion effect
    When PlayerTwo uses agent on PlayerOne
    Then PlayerOne is not infected
    Then PlayerTwo is not infected
    Then agent is used up

  Scenario: PlayerOne gets protected by glove, and PlayerTwo is not protected by a cape
    Given  there are two players on the same field
    Given a glove is equipped on PlayerOne
    Given a cape with 0% protection is equipped on PlayerTwo
    Given PlayerTwo has virus with confusion effect
    When PlayerTwo uses agent on PlayerOne
    Then PlayerOne is not infected
    Then PlayerTwo is infected
    Then agent is used up

