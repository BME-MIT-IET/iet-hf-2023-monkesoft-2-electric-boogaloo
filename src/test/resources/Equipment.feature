Feature: Equipment
  Scenario Outline: Equip
    Given a player is present
    Given <equipment> with <effect> is in player's equipment inventory
    When player equips the equipment
    Then the equipment is present in the equipped items inventory
    Then the equipment is not present in the equipment inventory
    Then the equipment's effect is on the player

    Examples:
      | equipment | effect            |
      | glove     | reflect           |
      | bag       | inventoryincrease |
      | cape      | individual        |
      | glove     | reflect           |

  Scenario: PlayerOne attacks PlayerTwo with a sharp axe
    Given There is a field
    Given There are two players on this field
    Given PlayerOne has a sharp axe in his inventory
    When PlayerOne uses axe on PlayerTwo
    Then PlayerTwo gets removed from field

  Scenario: PlayerOne attacks PlayerTwo with a blunt axe
    Given There is a field
    Given There are two players on this field
    Given PlayerOne has a sharp blunt in his inventory
    When PlayerOne uses axe on PlayerTwo
    Then PlayerTwo is not removed from field

