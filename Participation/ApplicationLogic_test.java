package Participation;

import org.junit.* ;
import static org.junit.Assert.* ;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * This is just a simple template for Junit test-class for testing
 * the class ApplicationLogic. Testing this class is a bit more
 * complicated. It uses database, which form an implicit part of
 * the state of ApplicationLogic. After each test case, you need to
 * reset the content of the database to the value it had, before
 * the test case. Otherwise, multiple test cases will interfere
 * with each other, which makes debugging hard should you find error.
 * 
 */
public class ApplicationLogic_test {

	/**
	 * Provide a functionality to reset the database. Here I will just
	 * delete the whole database file. 
	 */
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
//Provided Example Tests
	
//	
//	@Test
//	public void test1() {
//		// We'll always begin by reseting the database. This makes sure
//		// the test start from a clean, well defined state of the database.
//		// In this case it would be just an empty database, though it 
//		// doesn't have to be like that.
//		setupDB() ;
//		
//		System.out.println("** Testing add customer...") ;
//		
//		// Creating the target thing you want to test:
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		// Now let's perform some testing. If we add a customer to the system,
//		// test that this customer should then be really added to the system:
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		Customer C = SUT.findCustomer(duffyID) ;
//		assertTrue(C.name.equals("Duffy Duck")) ;
//		assertTrue(C.email.equals("")) ;		
//	}
//	
//	// Another example...
//	@Test
//	public void test2() {
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		System.out.println("** Testing getCostToPay ...") ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
//		// let Duffy but 2x participations on Flower-online:
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//
//		// Now let's check if the system correctly calculates the participation
//		// cost of Duffy:
//		Customer C = SUT.findCustomer(duffyID) ;
//		assertTrue(C.getCostToPay() == 200) ;
//	}
	
	//NEED TO TEST: REMOVE SERVICE && RESOLVE
	//Remove Service: 
		//remove service w/ given id
		//remove all participants to that service too
	
	//test that if a service is removed, the participant doesn't have it anymore
	@Test
	public void test3(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.removeService(flowerServiceID);
		
		Customer C = SUT.findCustomer(duffyID) ;

		assertTrue(C.getParticipations().size() == 0);
			
	}
	
	
	
	
	//Test that it does not remove customer participations in different services
		//if they are also participating in the removed service
	@Test
	public void test4(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int pizzaServiceID = SUT.addService("Pizza delivery", 150) ;

		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;

		SUT.removeService(flowerServiceID);
		//SUT.removeService(4);
		
		Customer C = SUT.findCustomer(duffyID) ;
		
		//even though the service "flowers online shop" was removed, 
		//customers participating in it should still exist and so should their participations
		assertTrue(C.getParticipations().iterator().next().getService().name.equals("Pizza delivery"));
	}
	
	//test that removing a service w/o the correct id will not crash?
	@Test
	public void test5(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int pizzaServiceID = SUT.addService("Pizza delivery", 150) ;

		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;
		SUT.removeService(4);
		
		
		assertTrue(SUT.getServices().size()==2);
		
	}
	
	//successfully remove a service that has no participations
	@Test
	public void test6(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		SUT.addCustomer("JOJO", "");
		int donutID = SUT.addService("Granny's Donuts", 50);
		SUT.removeService(donutID);
		
		assertTrue(SUT.getServices().size() == 0);
	}
	
	
	//RESOLVE
		//calculate all contribution costs
		//consume all applicable discount tokens
	
	//test to see if resolve will calculate contribution costs correctly
//	@Test
//	public void test6(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		Customer C = SUT.findCustomer(duffyID) ;
//		int custID = SUT.addCustomer("Customer One", "");
//		
//		Map<Customer,Integer> payment = SUT.resolve();
//		System.out.println(payment.get(C));
//		assertTrue(payment.get(C)==200);
//
//		
//	}
	}
	

