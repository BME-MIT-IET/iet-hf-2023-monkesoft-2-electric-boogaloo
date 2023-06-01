Feature: Player moves around the map
  Scenario: PlayerOne moves to a neighbouring field
    Given there are two players;
    Given There are four fields in the town, a lab a storage, a shelter and a field
    Given Field is a neighbour of laboratory
    Given Laboratory is a neighbour of shelter
    Given Shelter is a neighbour of storage
    Given PlayerOne is initially on the field
    When PlayerOne moves to laboratory
    Then PlayerOne is on laboratory
    Then PlayerOne is not on field
    Then PlayerOne is not on shelter

  Scenario: PlayerOne tries to move to a non-neighbouring field
    Given there are two players;
    Given There are four fields in the town, a lab a storage, a shelter and a field
    Given Field is a neighbour of laboratory
    Given Laboratory is a neighbour of shelter
    Given Shelter is a neighbour of storage
    Given PlayerOne is initially on the field
    When PlayerOne moves to shelter
    Then PlayerOne is on field
    Then PlayerOne is not on laboratory
    Then PlayerOne is not on shelter
    Then PlayerOne is not on storage

  Scenario: PlayerOne moves to a neighbouring field which PlayerTwo is on
    Given there are two players;
    Given There are four fields in the town, a lab a storage, a shelter and a field
    Given Field is a neighbour of laboratory
    Given Laboratory is a neighbour of shelter
    Given Shelter is a neighbour of storage
    Given PlayerOne is initially on the field
    Given PlayerTwo is initially on laboratory
    When PlayerOne moves to laboratory
    Then PlayerOne is on laboratory
    Then PlayerOne is not on field
    Then PlayerOne is not on shelter
    Then PlayerTwo is on laboratory