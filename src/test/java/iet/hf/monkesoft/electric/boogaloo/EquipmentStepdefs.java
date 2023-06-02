package iet.hf.monkesoft.electric.boogaloo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;
public class EquipmentStepdefs {

    Virologist player;
    Equipment equipment;
    Effect effect;

    Confusion dummyEffect = new Confusion();

    @Given("a player is present")
    public void aPlayerIsPresent() {
        player = new Virologist(0);
    }

    @Given("glove with reflect is in player's equipment inventory")
    public void glove_with_reflect_is_in_player_s_equipment_inventory() {
        effect = new Reflect();
        equipment = new Glove(effect, 0);
        player.getEquipmentInventory().add(equipment);
    }

    @Given("bag with inventoryIncrease is in player's equipment inventory")
    public void bag_with_inventoryIncrease_is_in_player_s_equipment_inventory() {
        effect = new InventoryIncrease();
        equipment = new Bag(effect, 0);
        player.AddEquipment(equipment);
    }

    @Given("cape with individual is in player's equipment inventory")
    public void cape_with_individual_is_in_player_s_equipment_inventory() {
        effect = new Individual(dummyEffect);
        equipment = new Cape(effect, 0);
        player.AddEquipment(equipment);
    }

    @When("player equips the equipment")
    public void player_equips_the_equipment() {
        player.Equip(equipment);
    }
    @Then("the equipment is present in the equipped items inventory")
    public void the_equipment_is_present_in_the_equipped_items_inventory() {
        Assert.assertTrue(player.getEquippedEquipments().contains(equipment));
    }

    @Then("the equipment's effect is on the player")
    public void theEquipmentSEffectIsOnThePlayer() {
        Assert.assertTrue(player.HasEffect(effect));
    }

    @Then("the equipment is not present in the equipment inventory")
    public void theEquipmentIsNotPresentInTheEquipmentInventory() {
        Assert.assertTrue (!player.getEquipmentInventory().contains(equipment));
    }


    Field field;
    Axe axe;
    Virologist player1;
    Virologist player2;
    @Given("There is a field")
    public void thereIsAField() {
        field = new Field(0);
    }

    @Given("There are two players on this field")
    public void thereAreTwoPlayersOnThisField() {
        player1 = new Virologist(1);
        player2 = new Virologist(2);

        player1.SetField(field);
        field.Add(player1);
        player2.SetField(field);
        field.Add(player2);
    }

    @Given("PlayerOne has a sharp axe in his inventory")
    public void playerOneHasASharpAxeInHisInventory() {
        axe = new Axe(0);
        player1.AddEquipment(axe);
    }

    @When("PlayerOne uses axe on PlayerTwo")
    public void playerOneUsesAxeOnPlayerTwo() {
        player1.getEquipmentInventory().get(0).Use(player2);
    }

    @Then("PlayerTwo gets removed from field")
    public void playerTwoGetsRemovedFromField() {
        Assert.assertTrue (!field.GetVirologists().contains(player2));
    }

    @Then("PlayerTwo is not removed from field")
    public void playertwoIsNotRemovedFromField() {
        Assert.assertTrue (field.GetVirologists().contains(player2));
    }

    @Given("PlayerOne has a blunt axe in his inventory")
    public void playeroneHasABluntAxeInHisInventory() {
        axe = new Axe(0);
        axe.Blunt();
        player1.AddEquipment(axe);
    }
}
