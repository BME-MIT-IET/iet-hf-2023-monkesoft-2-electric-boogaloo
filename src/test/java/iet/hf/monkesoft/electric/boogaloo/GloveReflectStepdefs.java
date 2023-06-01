package iet.hf.monkesoft.electric.boogaloo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;
public class GloveReflectStepdefs {

    Virologist playerOne;
    Virologist playerTwo;

    Glove glove1;

    Glove glove2;

    Field field;

    Confusion confusion;
    Virus virus;
    @Given("there are two players on the same field")
    public void thereAreTwoPlayersOnTheSameField() {
        field = new Field(0);
        playerOne = new Virologist(1);
        playerTwo = new Virologist(2);

        playerOne.SetField(field);
        playerTwo.SetField(field);
    }

    @Given("a glove is equipped on PlayerOne")
    public void aGloveIsEquippedOnPlayerOne() {

        Reflect reflect = new Reflect();
        glove1 = new Glove(reflect, 0);
        playerOne.AddEquipment(glove1);
        playerOne.getEquipmentInventory().get(0).Equipped(playerOne);
    }

    @Given("PlayerTwo has virus with confusion effect")
    public void playertwoHasVirusWithConfusionEffect() {
        confusion = new Confusion();
        virus = new Virus(confusion);

        playerTwo.AddAgent(virus);
    }

    @When("PlayerTwo uses agent on PlayerOne")
    public void playertwoUsesAgentOnPlayerOne() {
        playerTwo.getAgentInventory().get(0).Activate(playerTwo, playerOne);
    }

    @Then("PlayerOne is not infected")
    public void playeroneIsNotInfected() {
        Assert.assertTrue(!playerOne.HasEffect(confusion));
    }

    @Then("PlayerTwo is infected")
    public void playertwoIsInfected() {
        Assert.assertTrue(playerTwo.HasEffect(confusion));
    }

    @Then("agent is used up")
    public void agentIsUsedUp() {
        Assert.assertTrue(!playerTwo.getAgentInventory().contains(virus));
    }

    @Given("a glove is equipped on PlayerTwo")
    public void aGloveIsEquippedOnPlayerTwo() {
        Reflect reflect = new Reflect();
        glove2 = new Glove(reflect, 0);
        playerTwo.AddEquipment(glove2);
        playerTwo.getEquipmentInventory().get(0).Equipped(playerTwo);
    }

    @Then("PlayerTwo is not infected")
    public void playertwoIsNotInfected() {
        Assert.assertTrue(!playerTwo.getAgentInventory().contains(virus));
    }

    @Given("a cape with {double}% protection is equipped on PlayerTwo")
    public void aCapeWithProtectionIsEquippedOnPlayerTwo(double percentage) {

        General generalProtection = new General(percentage);
        Cape cape = new Cape(generalProtection, 0);
        playerTwo.AddEquipment(cape);
        playerTwo.getEquipmentInventory().get(0).Equipped(playerTwo);
    }
}
