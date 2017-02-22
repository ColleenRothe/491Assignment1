package Participation;

import static org.junit.Assert.*;

import org.junit.Test;

public class Discount_5pack_test {
	
	/**
	 * Provide a functionality to reset the database. Here I will just
	 * delete the whole database file. 
	 */
	private void setupDB() {
		Persistence.wipedb() ;
	}

	//NEED TO TEST: APPLICABLE && CALCDISCOUNT
	
	//APPLICABLE:
		//return true if discount token applicable on given customer
		//only applicable when customer participates in at least 5 services, >100 euros
	
	//return a standard applicable
	
	@Test
	//test an average working case
	public void test1(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int donutID = SUT.addService("Granny's Donuts", 100);
		int pizzaServiceID = SUT.addService("Pizza delivery", 150) ;
		int icecreamServiceID = SUT.addService("Ice Cream", 125);
		int pharmacyID = SUT.addService("Pharmacy", 125);
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;
		SUT.addParticipation(duffyID, icecreamServiceID) ;
		SUT.addParticipation(duffyID, pharmacyID) ;
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_5pack D = new Discount_5pack();
		
		assertTrue(D.applicable(C) == true);
	}
	
	@Test
	//test when there are only 4 services
	public void test2(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int donutID = SUT.addService("Granny's Donuts", 100);
		int pizzaServiceID = SUT.addService("Pizza delivery", 150) ;
		int icecreamServiceID = SUT.addService("Ice Cream", 125);
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;
		SUT.addParticipation(duffyID, icecreamServiceID) ;
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_5pack D = new Discount_5pack();
		//should not be applicable because only participating in 4 services
		assertTrue(D.applicable(C) == false);
		
	}
	
	@Test
	//test when there is a service < 100
	public void test3(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int donutID = SUT.addService("Granny's Donuts", 100);
		int pizzaServiceID = SUT.addService("Pizza delivery", 150) ;
		int icecreamServiceID = SUT.addService("Ice Cream", 125);
		int pharmacyID = SUT.addService("Pharmacy", 99);
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;
		SUT.addParticipation(duffyID, icecreamServiceID) ;
		SUT.addParticipation(duffyID, pharmacyID) ;
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_5pack D = new Discount_5pack();
		//should not be applicable because one of the services is <100
		assertTrue(D.applicable(C) == false);
		
	}
	
	//CALCDISCOUNT:
		//returns discount value in eurocent
		//token gives 10 euro discount

}
