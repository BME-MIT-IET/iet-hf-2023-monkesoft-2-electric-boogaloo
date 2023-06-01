package iet.hf.monkesoft.electric.boogaloo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class MovementStepdefs {


    Virologist player1;
    Virologist player2;
    Town town = new Town();
    Field field;
    Laboratory laboratory;

    Shelter shelter;

    Storage storage;

    @Given("There are four fields in the town, a lab a storage, a shelter and a field")
    public void thereAreFourFieldsInTheTownALabAStorageAShelterAndAField() {
        field = new Field(0);
        laboratory = new Laboratory(null, 1);
        shelter = new Shelter(null, 2);
        storage = new Storage(0,0,3);

        town = new Town();

        town.AddField(field);
        town.AddField(laboratory);
        town.AddField(shelter);
        town.AddField(storage);
    }


    @Given("Field is a neighbour of laboratory")
    public void fieldIsANeighbourOfLaboratory() {
        field.AddNeighbour(laboratory);
        laboratory.AddNeighbour(field);
    }

    @Given("Laboratory is a neighbour of shelter")
    public void laboratoryIsANeighbourOfShelter() {
        laboratory.AddNeighbour(shelter);
        shelter.AddNeighbour(laboratory);
    }

    @Given("Shelter is a neighbour of storage")
    public void shelterIsANeighbourOfStorage() {
        shelter.AddNeighbour(storage);
        storage.AddNeighbour(shelter);
    }

    @Then("PlayerOne is on the field")
    public void playeroneIsOnTheField() {
        Assert.assertTrue(field.GetVirologists().contains(player1));
    }

    @Then("PlayerOne is not on field")
    public void playeroneIsNotOnField() {
        Assert.assertTrue(!field.GetVirologists().contains(player1));
    }


    @Given("there are two players;")
    public void thereAreTwoPlayers() {
        player1 = new Virologist(1);
        player2 = new Virologist(2);
    }

    @When("PlayerOne moves to laboratory")
    public void playeroneMovesToLaboratory() {
        player1.Move(laboratory);
    }

    @Then("PlayerOne is on laboratory")
    public void playeroneIsOnLaboratory() {
        Assert.assertTrue (laboratory.GetVirologists().contains(player1));
    }

    @Then("PlayerOne is not on shelter")
    public void playeroneIsNotOnShelter() {
        Assert.assertTrue (!shelter.GetVirologists().contains(player1));
    }

    @When("PlayerOne moves to shelter")
    public void playeroneMovesToShelter() {
        player1.Move(shelter);
    }

    @Then("PlayerOne is on field")
    public void playeroneIsOnField() {
        Assert.assertTrue (field.GetVirologists().contains(player1));
    }

    @Then("PlayerOne is not on laboratory")
    public void playeroneIsNotOnLaboratory() {
       Assert. assertTrue (!laboratory.GetVirologists().contains(player1));
    }

    @Then("PlayerOne is not on storage")
    public void playeroneIsNotOnStorage() {
        Assert.assertTrue (!storage.GetVirologists().contains(player1));
    }

    @Then("PlayerTwo is on laboratory")
    public void playertwoIsOnLaboratory() {
        Assert.assertTrue (laboratory.GetVirologists().contains(player1));
    }


    @Given("PlayerTwo is initially on laboratory")
    public void playertwoIsInitiallyOnLaboratory() {
        laboratory.Add(player2);
        player2.SetField(laboratory);
    }

    @Given("PlayerOne is initially on the field")
    public void playeroneIsInitiallyOnTheField() {
        field.Add(player1);
        player1.SetField(field);
    }
}
