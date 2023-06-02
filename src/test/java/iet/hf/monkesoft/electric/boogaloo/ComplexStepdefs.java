package iet.hf.monkesoft.electric.boogaloo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ComplexStepdefs {

    int fieldId = 0;
    int laboratoryId = 1;
    int shelterId = 2;
    int storageId = 3;
    Town town;
    Virologist playerOne;
    Virologist playerTwo;

    Field field;
    Laboratory laboratory;
    Shelter shelter;

    Storage storage;

    Axe axe;

    GeneticCode geneticCode;

    Effect effect;

    int player1VirusId = 101;
    int player2VirusId = 102;

    int player1VaccineId = 103;

    @Given("there is a shelter with a sharp axe, a laboratory with a genetic code, and a field")
    public void thereIsAShelterALaboratoryAndAField() {
        geneticCode = new GeneticCode("dummy", null, null);
        axe = new Axe(0);

        laboratory = new Laboratory(geneticCode, laboratoryId);
        field = new Field(fieldId);
        shelter = new Shelter(axe,shelterId);
    }

    @Given("field is next to laboratory")
    public void fieldIsNextToLaboratory() {
        field.AddNeighbour(laboratory);
        laboratory.AddNeighbour(field);
    }

    @Given("shelter is next to laboratory")
    public void shelterIsNextToLaboratory() {
        shelter.AddNeighbour(laboratory);
        laboratory.AddNeighbour(shelter);
    }

    @Given("playerOne is on the field")
    public void playeroneIsOnTheField() {
        field.Add(playerOne);
        playerOne.SetField(field);
    }

    @Given("PlayerTwo is in the shelter")
    public void playertwoIsInTheShelter() {
        playerTwo.SetField(shelter);
        shelter.Add(playerTwo);
    }

    @When("PlayerTwo picks up the axe")
    public void playertwoPicksUpTheAxe() {
        shelter.Interacted(playerTwo);
    }

    @When("PlayerOne steps on laboratory")
    public void playeroneStepsOnLaboratory() {
        playerOne.Move(laboratory);
    }

    @When("PlayerOne interacts with laboratory")
    public void playeroneInteractsWithLaboratory() {
        laboratory.Interacted(playerOne);
    }

    @Then("PlayerOne learned the genetic code")
    public void playeroneLearnedTheGeneticCode() {
        Assert.assertTrue(playerOne.getCodeList().contains(geneticCode));
    }

    @When("PlayerTwo steps on laboratory")
    public void playertwoStepsOnLaboratory() {
        playerTwo.Move(laboratory);
    }

    @When("PlayerTwo kills PlayerOne")
    public void playertwoKillsPlayerOne() {
        playerTwo.getEquipmentInventory().get(0).Use(playerOne);
    }

    @Then("PlayerOne is not on the laboratory field")
    public void playeroneIsNotOnTheField() {
        Assert.assertTrue(!laboratory.GetVirologists().contains(playerOne));
    }

    @Then("PlayerTwo is on the laboratory")
    public void playertwoIsOnTheLaboratory() {
        Assert.assertTrue(laboratory.GetVirologists().contains(playerTwo));
    }


    @Then("the axe is blunted")
    public void theAxeIsBlunted() {
        Assert.assertTrue(!axe.getIsSharp());
    }

    @When("PlayerTwo interacts with laboratory")
    public void playertwoInteractsWithLaboratory() {
        laboratory.Interacted(playerTwo);
    }

    @Then("PlayerTwo learned the genetic code")
    public void playertwoLearnedTheGeneticCode() {
        Assert.assertTrue(playerTwo.getCodeList().contains(geneticCode));
    }

    @Then("the axe is no longer in the shelter")
    public void theAxeIsNoLongerInTheShelter() {
        Assert.assertTrue(shelter.getEuipment() == null);
    }

    @Given("there are two players,PlayerOne and PlayerTwo")
    public void thereAreTwoPlayersPlayerOneAndPlayerTwo() {
        playerOne = new Virologist(1);
        playerTwo = new Virologist(2);
    }

    @Given("there is a field")
    public void thereIsAField() {
        field = new Field(fieldId);
    }

    @Given("there is a storage with max of each resource")
    public void thereIsAStorageWithOfEachResource() {
        storage = new Storage(AminoAcid.MAX, Nucleotid.MAX, storageId);
    }

    @Given("there is a laboratory with a genetic code")
    public void thereIsALaboratoryWithAGeneticCode() {
        effect = new Confusion();

        geneticCode = new GeneticCode("Confusion", effect, effect);
        laboratory = new Laboratory(geneticCode, 0);
    }

    @Given("playerTwo is on the field")
    public void playertwoIsOnTheField() {
        field.Add(playerTwo);
        playerTwo.SetField(field);
    }

    @Given("playerTwo knows the genetic code")
    public void playertwoKnowsTheGeneticCode() {
        playerTwo.AddGenCode(geneticCode);
    }

    @Given("field is next to storage")
    public void fieldIsNextToStorage() {
        field.AddNeighbour(storage);
        storage.AddNeighbour(field);
    }

    @When("PlayerOne steps on storage")
    public void playeroneStepsOnStorage() {
        playerOne.Move(storage);
    }

    @When("PlayerOne interacts with storage, taking resources")
    public void playeroneInteractsWithStorageTakingOfEachResource() {
        storage.Interacted(playerOne);
    }

    @Then("PlayerOne acquired resources")
    public void playeroneAcquiredOfEachResource() {
        for(Material material: playerOne.getMaterialList()){
            Assert.assertTrue(material.getAmount() > 0);
        }
    }

    @When("PlayerOne steps on field")
    public void playeroneStepsOnField() {
        playerOne.Move(field);
    }

    @When("PlayerOne creates a virus")
    public void playeroneCreatesAVirus() {
        playerOne.MakeVirus(playerOne.getCodeList().get(0), player1VirusId);
    }

    @When("PlayerOne creates a vaccine")
    public void playeroneCreatesAVaccine() {
        playerOne.MakeVaccine(playerOne.getCodeList().get(0), player1VaccineId);
    }

    @Then("Vaccine is in PlayerOne's inventory")
    public void vaccineIsInPlayerOneSInventory() {
        boolean foundVaccine = false;
        for(Agent agent: playerOne.getAgentInventory()){
            if(agent.Id == player1VaccineId){
                foundVaccine = true;
                break;
            }
        }
        Assert.assertTrue(foundVaccine);
    }

    @Then("Virus is in PlayerOne's inventory")
    public void virusIsInPlayerOneSInventory() {
        boolean foundVirus = false;
        for(Agent agent: playerOne.getAgentInventory()){
            if(agent.Id == player1VirusId) foundVirus = true;
            break;
        }
        Assert.assertTrue(foundVirus);
    }

    @When("PlayerTwo has a virus with genetic code")
    public void playertwoHasAVirusWithGeneticCode() {
        playerTwo.AddGenCode(geneticCode);
        for(Material material: playerTwo.getMaterialList()){
            material.AddAminoAcid(AminoAcid.MAX);
            material.AddNucleotid(Nucleotid.MAX);
        }
        playerTwo.MakeVirus(playerTwo.getCodeList().get(0), player2VirusId);
    }

    @When("PlayerTwo uses virus on PlayerOne")
    public void playertwoUsesVirusOnPlayerOne() {
        for(Agent agent: playerTwo.getAgentInventory()){
           if(agent.Id == player2VirusId){
               playerTwo.UseAgent(agent, playerOne);
               break;
           };
        }
    }

    @Then("virus is removed from PlayerTwo's inventory")
    public void virusIsRemovedFromPlayerTwoSInventory() {
        for(Agent agent: playerTwo.getAgentInventory()){
            Assert.assertTrue(agent.Id != player2VirusId);
        }
    }

    @Then("PlayerOne is infected")
    public void playeroneIsInfected() {
        Assert.assertTrue(playerOne.getAe().size() > 0);
    }

    @When("PlayerOne uses vaccine")
    public void playeroneUsesVaccine() {
        for(Agent agent: playerOne.getAgentInventory()){
            if(agent.Id == player1VaccineId){
                playerOne.UseAgent(agent, playerOne);
                break;
            }
        }
    }

    @Then("PlayerOne is cured")
    public void playeroneIsCured() {
            Assert.assertFalse(playerOne.HasEffect(effect));
    }

    @Then("vaccine is removed from PlayerOne's inventory")
    public void vaccineIsRemovedFromPlayerOneSInventory() {
        for(Agent agent: playerOne.getAgentInventory()){
            Assert.assertFalse(agent.Id == player1VaccineId);
        }
    }

    @When("PlayerOne uses virus on PlayerTwo")
    public void playeroneUsesVirusOnPlayerTwo() {
        for(Agent agent: playerOne.getAgentInventory()){
            if(agent.Id == player1VirusId){
                playerOne.UseAgent(agent, playerTwo);
                break;
            }
        }
    }

    @Then("virus is removed from PlayerOne's inventory")
    public void virusIsRemovedFromPlayerOneSInventory() {
        for(Agent agent: playerOne.getAgentInventory()){
            Assert.assertFalse(agent.Id == player1VirusId);
        }
    }

    @Then("playerTwo is infected")
    public void playertwoIsInfected() {
       Assert.assertTrue(playerTwo.HasEffect(effect));
    }
}
