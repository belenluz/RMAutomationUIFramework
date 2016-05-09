package org.fundacionjala.automation.framework.maps.admin.emailserver;

public class EmailServerMap {
	public static final String ADD_BUTTON = "//span[text()='Add']/parent::button[@class='btn btn-clear info']";
	public static final String REMOVE_BUTTON = "//span[text()='Remove']/parent::button[@class='btn btn-clear']";
	public static final String EMAIL_SERVER_BUTTON = "//a[@ng-click='serverSelected($index)'][@ng-repeat='item in mailservers']";
	public static final String EDIT_BUTTON = "//span[text()='Edit']/parent::button[@class='btn btn-default'][@ng-click='edit()']";
	public static final String USERNAME_TEXT_FIELD = "//input[@id='credential-username'][@type='text']";
	public static final String PASSWORD_TEXT_FIELD = "//input[@id='credential-password'][@type='password']";
	public static final String ACCEPT_BUTTON = "//span[text()='Accept']/parent::button[@class='btn btn-primary'][@ng-click='save()']";
	public static final String DESCRIPTION_TEXT_FIELD = "//textarea[@id='mailserver-description'][@ng-model='currentSelected.description']";
}
