Feature:  CloudAPI Tests

  @CloudAPI
  Scenario: GET request to get id of Folder name "HP"
    Given I make a GET request at "/public-api/v1/folders"
    Then The response should contain the Folder id for the "HP" folder
    Then The request should receive status 200

  @CloudAPI
  Scenario: GET request to get Configuration id of folder name "HP"
    Given I make a GET request at "/file-processing-configurations"
    Then The response should contain the Configuration id for the "HP" folder
    Then The request should receive status 200

  @CloudAPI
  Scenario: GET request to get TranslationEngine id of folder name "HP"
    Given I make a GET request at "/translation-engines"
    Then The response should contain the Translation Engine id for the "HP" folder
    Then The request should receive status 200

  @CloudAPI
  Scenario: GET request to get PricingModel id of folder name "HP"
    Given I make a GET request at "/pricing-models"
    Then The response should contain the PricingModel id for the "HP" folder
    Then The request should receive status 200

  @CloudAPI
  Scenario: GET request to get Workflow id of folder name "HP"
    Given I make a GET request at "/workflows"
    Then The response should contain the Workflow id for the "HP" folder
    Then The request should receive status 200

  @CloudAPI
  Scenario: POST request to create a new Project with retrieved ids
    Given I make a POST request at "/projects"
    Then The request should receive status 201
    And The response body should contain a project id

  @CloudAPI
  Scenario: Parse a file using a source-files POST request
    Given I parse a file using a POST request at source-files
    Then The request should receive status 201
    And The response body should contain relevant fields


