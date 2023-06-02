Feature: Some complex scenarios
  Scenario:
    PlayerTwo picks up an axe from the shelter field which he's on.
    PlayerOne steps on a neighbour laboratory.
    PlayerOne interacts with the laboratory, learning the genetic code.
    PlayerTwo steps on the same field PlayerOne is on.
    PlayerTwo kills PlayerOne with an axe.
    PlayerTwo interacts with the laboratory, learning the genetic code.

    Given there is a shelter with a sharp axe, a laboratory with a genetic code, and a field
    Given there are two players,PlayerOne and PlayerTwo
    Given field is next to laboratory
    Given shelter is next to laboratory
    Given playerOne is on the field
    Given PlayerTwo is in the shelter
    When PlayerTwo picks up the axe
    Then the axe is no longer in the shelter
    When PlayerOne steps on laboratory
    When PlayerOne interacts with laboratory
    Then PlayerOne learned the genetic code
    When PlayerTwo steps on laboratory
    When PlayerTwo kills PlayerOne
    Then PlayerOne is not on the laboratory field
    Then PlayerTwo is on the laboratory
    Then the axe is blunted
    When PlayerTwo interacts with laboratory
    Then PlayerTwo learned the genetic code

  Scenario:
    PlayerOne acquires resources from a storage, then learns a
    genetic code, the uses the resources to create a virus and a vaccine.
    PlayerTwo has a virus which he created with the same genetic code, steps to
    PlayerOne and uses the virus on him.
    PlayerOne uses the vaccine to cure himself, then uses the
    Virus to infect PlayerTwo.
    Given there is a field
    Given there is a storage with max of each resource
    Given there is a laboratory with a genetic code
    Given there are two players,PlayerOne and PlayerTwo
    Given playerOne is on the field
    Given playerTwo is on the field
    Given playerTwo knows the genetic code
    Given field is next to laboratory
    Given field is next to storage
    When PlayerOne steps on storage
    When PlayerOne interacts with storage, taking resources
    Then PlayerOne acquired resources
    When PlayerOne steps on field
    When PlayerOne steps on laboratory
    When PlayerOne interacts with laboratory
    Then PlayerOne learned the genetic code
    When PlayerOne creates a virus
    When PlayerOne creates a vaccine
    Then Vaccine is in PlayerOne's inventory
    Then Virus is in PlayerOne's inventory
    When PlayerTwo has a virus with genetic code
    When PlayerTwo steps on laboratory
    When PlayerTwo uses virus on PlayerOne
    Then virus is removed from PlayerTwo's inventory
    Then PlayerOne is infected
    When PlayerOne uses vaccine
    Then PlayerOne is cured
    Then vaccine is removed from PlayerOne's inventory
    When PlayerOne uses virus on PlayerTwo
    Then playerTwo is infected
    Then virus is removed from PlayerOne's inventory





