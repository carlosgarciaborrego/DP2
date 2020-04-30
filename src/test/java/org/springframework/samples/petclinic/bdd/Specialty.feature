Feature: Add and delete a specialty to a vet
	As an admin I can add and delete a specialty
	
	Scenario: Successful add and delete a specialty
		Given: I am logged in the system as admin
		When: I add and delete a specialty
		Then: I see the same specialty to the same vet