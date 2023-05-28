package iet.hf.monkesoft.electric.boogaloo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GloveReflectStepdefs {

    Virologist playerOne;
    Virologist playerTwo;

    Glove glove;

    Field field;

    Amnesia amnesia;
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
        glove = new Glove(reflect, 0);
        playerOne.AddEquipment(glove);
        playerOne.getEquipmentInventory().get(0).Equipped(playerOne);
    }

    @Given("PlayerTwo has virus with amnesia effect")
    public void playertwoHasVirusWithAmnesiaEffect() {
        amnesia = new Amnesia();
        virus = new Virus(amnesia);

        playerTwo.AddAgent(virus);
    }

    @When("PlayerTwo uses agent on PlayerOne")
    public void playertwoUsesAgentOnPlayerOne() {
        playerTwo.getAgentInventory().get(0).Activate(playerTwo, playerOne);
    }

    @Then("PlayerOne is not infected")
    public void playeroneIsNotInfected() {
        assert(!playerOne.HasEffect(amnesia));
    }

    @Then("PlayerTwo is infected")
    public void playertwoIsInfected() {
        assert(playerTwo.HasEffect(amnesia));
    }

    @Then("agent is used up")
    public void agentIsUsedUp() {
        assert(!playerTwo.getAgentInventory().contains(virus));
    }
}
