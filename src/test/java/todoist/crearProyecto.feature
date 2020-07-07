Feature: Crear un proyecto en todoist

  Scenario: Add project from plus button
    Given I am in todist website
    When I add a new project form the "el icono de +"
    Then The new project is listed in the end

  Scenario: Add project from "Add project" link
    Given I am in todist website
    When I add a new project form the "Add project link"
    Then The new project is listed in the end

#  Scenario: Add project from Add project Above option from 3 dots menu

#  Scenario: Add project from Add project Below option from 3 dots menu

#  Scenario: Create project from new task from Inbox
