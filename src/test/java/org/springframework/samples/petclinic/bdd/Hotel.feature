Feature: Manage a hotel
	As an admin I can manage a hotel
	
	Scenario: Successful manage a hotel (Positive)
		Given: I am logged in the system as admin
		When: I add, edit and delete a hotel
		Then: the view is the same that when I managed the hotel