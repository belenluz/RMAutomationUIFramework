package org.fundacionjala.automation.scenario.steps.admin.location;

import java.util.List;

import org.fundacionjala.automation.framework.pages.admin.home.AdminPage;
import org.fundacionjala.automation.framework.pages.admin.locations.LocationAssociationPage;
import org.fundacionjala.automation.framework.pages.admin.locations.LocationPage;
import org.fundacionjala.automation.framework.pages.admin.locations.UpdateLocationPage;
import org.fundacionjala.automation.framework.utils.api.managers.LocationAPIManager;
import org.fundacionjala.automation.framework.utils.api.objects.admin.Location;
import org.fundacionjala.automation.framework.utils.common.BrowserManager;
import org.fundacionjala.automation.framework.utils.common.DatabaseConnection;
import org.fundacionjala.automation.framework.utils.common.PropertiesReader;
import org.testng.Assert;

import com.mongodb.DBCollection;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;

public class LocationThenSteps {

	@Then("^The location \"([^\"]*)\" is displayed on location page$")
	public void verifyDisplayedLocation(String name) throws Throwable {

		LocationPage locationPage = new LocationPage();
		AdminPage admin = new AdminPage();
		admin.leftMenu.clickOnConferenceRoomsButton();
		admin.leftMenu.clickOnLocationsButton();

		Assert.assertTrue(locationPage.verifyLocationIsDisplayed(name));

	}

	@Then("^All locations added are displayed even \"([^\"]*)\" location$")
	public void verifyAllDiplayedLocations(String name) throws Throwable {

		LocationPage locationPage = new LocationPage();
		List<Location> locations = LocationAPIManager
				.getRequest(PropertiesReader.getServiceURL() + "/locations");

		for (Location location : locations) {
			Assert.assertTrue(locationPage
					.verifyLocationIsDisplayed(location.name));
		}

	}

	@Then("^The location display name \"([^\"]*)\" is displayed on location page$")
	public void verifyLocationName(String displayName) throws Throwable {

		LocationPage locationPage = new LocationPage();
		Assert.assertTrue(locationPage
				.verifyLocationIsDisplayedByDisplayName(displayName));

	}

	@Then("^The description \"([^\"]*)\" of location \"([^\"]*)\" is displayed on update location page$")
	public void verifyLocationDescription(String description, String displayName)
			throws Throwable {

		LocationPage locationPage = new LocationPage();
		UpdateLocationPage updateLocationPage = new UpdateLocationPage();

		locationPage.leftMenu.clickOnConferenceRoomsButton();

		updateLocationPage = locationPage.leftMenu.clickOnLocationsButton()
				.doubleClickOnALocation(displayName);

		Assert.assertTrue(updateLocationPage
				.verifyDescriptionIsDisplayed(description));

	}

	@Then("^The parent name \"([^\"]*)\" of location \"([^\"]*)\" is displayed on update location page$")
	public void verifyLocationParent(String parentName, String displayName)
			throws Throwable {

		AdminPage admin = new AdminPage();
		LocationPage locationPage = new LocationPage();
		UpdateLocationPage updateLocationPage = new UpdateLocationPage();

		admin.leftMenu.clickOnConferenceRoomsButton();
		admin.leftMenu.clickOnLocationsButton();

		updateLocationPage = locationPage.doubleClickOnALocation(displayName);

		Assert.assertTrue(updateLocationPage
				.verifyParentIsDisplayed(parentName));

	}

	@Then("^The location \"([^\"]*)\" is not displayed on location page$")
	public void verifyNotDisplayedLocation(String name) throws Throwable {

		LocationPage locationPage = new LocationPage();
		locationPage.leftMenu.clickOnConferenceRoomsButton();

		locationPage.leftMenu.clickOnLocationsButton();
		Assert.assertTrue(locationPage.verifyLocationIsNotDisplayed(name));
	}

	@Then("^The location \"([^\"]*)\" child of \"([^\"]*)\" is displayed on location page$")
	public void verifyChildLocation(String name, String parentName)
			throws Throwable {

		AdminPage admin = new AdminPage();
		admin.leftMenu.clickOnConferenceRoomsButton();
		admin.leftMenu.clickOnLocationsButton();

		LocationPage locationPage = new LocationPage();

		Assert.assertTrue(locationPage.verifyLocationIsDisplayed(name));
	}

	@Then("^The room \"([^\"]*)\" is displayed on Location Association page as associated with \"([^\"]*)\" location$")
	public void verifyAssociatedRoom(String roomName, String displayName)
			throws Throwable {

		LocationPage locationPage = new LocationPage();
		LocationAssociationPage associationPage = new LocationAssociationPage();

		AdminPage admin = new AdminPage();
		admin.leftMenu.clickOnConferenceRoomsButton();

		associationPage = locationPage.leftMenu.clickOnLocationsButton()
				.doubleClickOnALocation(displayName)
				.clickOnLocationAssociationLink().setRoomName(roomName)
				.clickOnAssigned();

		Assert.assertTrue(associationPage
				.verifyAssociatedRoomDisplayed(roomName));

	}

	@Then("^The room \"([^\"]*)\" is not displayed on Location Association page as associated with \"([^\"]*)\" location$")
	public void verifyNotAssociatedRoom(String roomName, String displayName)
			throws Throwable {

		LocationPage locationPage = new LocationPage();
		LocationAssociationPage associationPage = new LocationAssociationPage();

		locationPage.leftMenu.clickOnConferenceRoomsButton();

		locationPage.leftMenu.clickOnLocationsButton();

		associationPage = locationPage.doubleClickOnALocation(displayName)
				.clickOnLocationAssociationLink();

		Assert.assertTrue(associationPage
				.verifyAvailableRoomDisplayed(roomName));

	}

	@Then("^The number of associations on Location page has been increased by \"([^\"]*)\" location association$")
	public void verifyAssociationNumber(String name) throws Throwable {

		LocationPage locationPage = new LocationPage();
		BrowserManager.getDriver().navigate().refresh();
		locationPage.leftMenu.clickOnConferenceRoomsButton();

		locationPage.leftMenu.clickOnLocationsButton();
		Assert.assertTrue(locationPage.verifyNumberOfAssociations(name, "1"));
	}

	@Then("^The number of associations on Location page has been decreased by removing \"([^\"]*)\" location association$")
	public void verifyAssociationNumberDecrease(String name) throws Throwable {
		LocationPage locationPage = new LocationPage();
		BrowserManager.getDriver().navigate().refresh();
		locationPage.leftMenu.clickOnConferenceRoomsButton();

		locationPage.leftMenu.clickOnLocationsButton();
		Assert.assertTrue(locationPage.verifyNumberOfAssociations(name, "0"));
	}

	@Then("^An error message should be displayed$")
	public void an_error_message_should_be_displayed() throws Throwable {
		UpdateLocationPage updateLocationPage = new UpdateLocationPage();
		Assert.assertTrue(updateLocationPage.verifyErrorMessageDisplayed(),
				"The error message has been not displayed: Test Failed");
		updateLocationPage.clickOnCancelButton();
	}

	@Then("^Validate that the location table size is same than the option \"([^\"]*)\" selected$")
	public void validateTableSizeSelected(String sizeTable) throws Throwable {
		LocationPage location = new LocationPage();
		Assert.assertTrue(location.verifyNumberOfLocations(sizeTable));
	}
	
	@Then("^I validate if the corresonding page is displayed on Location page according the page size specified \"([^\"]*)\" and the page \"([^\"]*)\"$")
	public void validateCorresondingPageIsDisplayed(String pageSize, String page) throws Throwable {
		LocationPage location = new LocationPage();

		boolean verification = false;
		List<Location> listLocations = LocationAPIManager
				.getRequest(PropertiesReader.getServiceURL() + "/locations");
		int index = ((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize)) + 1;

		for (int i = 0; i < listLocations.size(); i++) {
			if (listLocations.get(i).name.contains(Integer.toString(index))) {
				String nameLocation = listLocations.get(i).name;
				if (location.getFirstRow().contains(nameLocation)) {
					verification = true;
				}
			}
		}
		Assert.assertTrue(verification,
				"The corresponding page isn't displaying according page size");
	}

	@After("@location")
	public void deleteAllLocations() {

		DatabaseConnection connection = new DatabaseConnection();
		connection.switchCollection("locations");
		DBCollection myCollection = connection.getCollection();
		myCollection.drop();
	}

}
