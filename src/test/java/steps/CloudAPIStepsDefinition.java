package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CloudAPIStepsDefinition {

    private final String baseUrl = "https://test.com";
    private Response response;

    private String folderId = "dummy_232131231";

    private String configurationId = "dummy_232131231";

    private String translationEngineID = "dummy_232131231";

    private String pricingModelID = "dummy_232131231";

    private String workflowID = "dummy_232131231";

    String projectId = "dummy_232131231";


    private String generateAuthorisationToken() {
        String endpoint = "/authenticate";
        String tokens = "{ " +
                "\"client_id\": \"123\"," +
                " \"client_secret\": \"123\"," +
                " \"grant_type\": \"123\"," +
                " \"audience\": \"https://test\" " +
                "}";
        Response tokenResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(tokens)
                .post("https://test.com");

        return tokenResponse.jsonPath().getString("access_token");
    }


    @When("I make a POST request at {string}")
    public void makePostRequest(String endpoint) {

        String payload = "{\n" +
                "  \"name\": \"IoanProject1\",\n" +
                "  \"description\": \"<string>\",\n" +
                "  \"dueBy\": \"2025-01-12T12:00:00.000Z\",\n" +
                "  \"projectTemplate\": {\n" +
                "    \"id\": \"<string>\"\n" +
                "  },\n" +
                "  \"languageDirections\": [\n" +
                "    {\n" +
                "      \"sourceLanguage\": {\n" +
                "        \"languageCode\": \"en-US\"\n" +
                "      },\n" +
                "      \"targetLanguage\": {\n" +
                "        \"languageCode\": \"ur-IN\"\n" +
                "      }\n" +
                "    }\n" +
                "      \n" +
                "  ],\n" +
                "  \"location\": \"" + folderId + "\",\n" +
                "  \"translationEngine\": {\n" +
                "    \"id\": \"" + translationEngineID + "\",\n" +
                "    \"strategy\": \"copy\"\n" +
                "  },\n" +
                "  \"fileProcessingConfiguration\": {\n" +
                "    \"id\": \"" + configurationId + "\",\n" +
                "    \"strategy\": \"copy\"\n" +
                "  },\n" +
                "  \"workflow\": {\n" +
                "    \"id\": \"" + workflowID + "\",\n" +
                "    \"strategy\": \"copy\"\n" +
                "  },\n" +
                "  \"pricingModel\": {\n" +
                "    \"id\": \"" + pricingModelID + "\",\n" +
                "    \"strategy\": \"copy\"\n" +
                "  }\n" +
                "}";


        String token = generateAuthorisationToken();

        response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("X-LC-Tenant", "test")
                .accept("*/*")
                .body(payload)
                .log().all()
                .post(baseUrl + endpoint);

    }

    @Given("I parse a file using a POST request at source-files")
    public void iParseAFileUsingAPOSTRequestAtSourceFiles() {


        File file = new File("src/test/resources/TestFiles/cloudAPI.txt");

        String token = generateAuthorisationToken();

        System.out.println(baseUrl + "/projects/" + projectId + "/source-files");

        Response response = RestAssured.given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "multipart/form-data")
                .header("X-LC-Tenant", "6036396dc508d16fa83e15b0")
                .multiPart("file", file)
                .formParam("name", "IoanSourceFile")
                .formParam("role", "translatable")
                .formParam("type", "native")
                .formParam("language", "en-US")
                .post(baseUrl + "/projects/" + projectId + "/source-files");

        System.out.println(response.getBody().asString());

    }


    @Then("The request should receive status {int}")
    public void checkStatusCode(int expectedHttpCode) {
        int receivedStatus = response.getStatusCode();
        assertEquals(expectedHttpCode, receivedStatus);
    }


    @Given("I make a GET request at {string}")
    public void iMakeAGETRequestAt(String path) {
        response = (Response) RestAssured.get(baseUrl + path).then().log().all();
    }


    @Then("The response should contain the Folder id for the {string} folder")
    public void theResponseShouldContainTheFolderIdForTheFolder(String folderName) {
        JsonPath jsonPath = response.jsonPath();
        folderId = jsonPath.get("items.find { it.name == " + folderName + " }.id");
        System.out.println("HP folder has the Folder id: " + folderId);
    }

    @Then("The response should contain the Configuration id for the {string} folder")
    public void theResponseShouldContainTheConfigurationIdForTheFolder(String folderName) {
        JsonPath jsonPath = response.jsonPath();
        configurationId = jsonPath.get("items.find { it.name == " + folderName + " }.id");
        System.out.println("HP folder has the Configuration id: " + configurationId);
    }

    @Then("The response should contain the Translation Engine id for the {string} folder")
    public void theResponseShouldContainTheTranslationEngineIdForTheFolder(String folderName) {
        JsonPath jsonPath = response.jsonPath();
        translationEngineID = jsonPath.get("items.find { it.name == " + folderName + " }.id");
        System.out.println("HP folder has the Translation Engine id: " + translationEngineID);
    }

    @Then("The response should contain the PricingModel id for the {string} folder")
    public void theResponseShouldContainThePricingModelIdForTheFolder(String folderName) {
        JsonPath jsonPath = response.jsonPath();
        pricingModelID = jsonPath.get("items.find { it.name == " + folderName + " }.id");
        System.out.println("HP folder has the PricingModel id: " + pricingModelID);
    }

    @Then("The response should contain the Workflow id for the {string} folder")
    public void theResponseShouldContainTheWorkflowIdForTheFolder(String folderName) {
        JsonPath jsonPath = response.jsonPath();
        workflowID = jsonPath.get("items.find { it.name == " + folderName + " }.id");
        System.out.println("HP folder has the Workflow id: " + workflowID);
    }

    @And("The response body should contain a project id")
    public void theResponseBodyShouldContainTheField() {
        JsonPath jsonPath = response.jsonPath();
        String projectId = jsonPath.get("id");
        System.out.println("The response body contains the " + projectId + " field");
    }


    @And("The response body should contain relevant fields")
    public void theResponseBodyShouldContainRelevantFields() {

        response.then().body("id", Matchers.not(Matchers.emptyOrNullString()));
        response.then().body("name", Matchers.not(Matchers.emptyOrNullString()));
        response.then().body("role", Matchers.not(Matchers.emptyOrNullString()));
        response.then().body("language.languageCode", Matchers.not(Matchers.emptyOrNullString()));
        response.then().body("version.id", Matchers.not(Matchers.emptyOrNullString()));
        response.then().body("version.type", Matchers.not(Matchers.emptyOrNullString()));

    }

}
