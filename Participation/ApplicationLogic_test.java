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
		//customer should no longer have this participation, because service was removed
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
	
	//Test that attempting to remove a service with a non-existent id will not remove others
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
		
		//should still have the two services, tried to remove one that doesn't exist
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
		
		//removed a servicethat had no participations, should be none left. 
		assertTrue(SUT.getServices().size() == 0);
	}
	
	
	//RESOLVE
		//calculate all contribution costs
		//consume all applicable discount tokens
	
	//test on average case to see if resolve will calculate contribution cost correctly	
		//and to see if resolve will calculate discount tokens correctly for D5p
	@Test
	public void test7(){
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
		System.out.println(SUT.discount(duffyID));
		SUT.awardDiscount(duffyID, SUT.D5p);
		Map<Customer,Integer> payment = SUT.resolve();
		//should be able to apply a 10 euro discount, should take away that discount
		assertTrue(payment.get(payment.keySet().iterator().next())==590);
		assertTrue(SUT.discount(duffyID) == 0);	
	}
	
	//test D5p when there is only 4 participations (need 5)
	@Test
	public void test8(){
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

		SUT.awardDiscount(duffyID, SUT.D5p);
		
		Map<Customer,Integer> payment = SUT.resolve();
		//discount should not be applied, because there are only 4 participations
		assertTrue(payment.get(payment.keySet().iterator().next())==475);
		assertTrue(SUT.discount(duffyID) == 0);	
		
	}
	
	//test on average case to see if resolve will calculate contribution cost correctly	
	//and to see if resolve will calculate discount tokens correctly for D1000
	@Test
	public void test9(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;
		Customer C = SUT.findCustomer(duffyID);
		
		SUT.awardDiscount(duffyID, SUT.D1000);
		Map<Customer,Integer> payment = SUT.resolve();
		//should be able to apply 2 50-discounts, and take away that discount
		assertTrue(SUT.discount(duffyID) == 0);	
		assertTrue(payment.get(payment.keySet().iterator().next())==190000);
			
	}
	
	//test D1000 when there is only 99,999 in participation
	@Test
	public void test10(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 99999) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		SUT.awardDiscount(duffyID, SUT.D1000);
		Map<Customer,Integer> payment = SUT.resolve();
		
		//discount token should not be applied, have to pay full amount
		assertTrue(SUT.discount(duffyID) == 0);	
		assertTrue(payment.get(payment.keySet().iterator().next())==99999);
			
	}
	
	//test when there is a combination of both discount tokens
	@Test
	public void test11(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
		int donutID = SUT.addService("Granny's Donuts", 100000);
		int pizzaServiceID = SUT.addService("Pizza delivery", 100000) ;
		int icecreamServiceID = SUT.addService("Ice Cream", 100000);
		int pharmacyID = SUT.addService("Pharmacy", 100000);
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID) ;
		SUT.addParticipation(duffyID, pizzaServiceID) ;
		SUT.addParticipation(duffyID, icecreamServiceID) ;
		SUT.addParticipation(duffyID, pharmacyID) ;
		System.out.println(SUT.discount(duffyID));
		SUT.awardDiscount(duffyID, SUT.D5p);
		SUT.awardDiscount(duffyID, SUT.D1000);
		Map<Customer,Integer> payment = SUT.resolve();
		
		//should be apply to apply multiple types of discount, and take them away
		assertTrue(payment.get(payment.keySet().iterator().next())==474990);
		assertTrue(SUT.discount(duffyID) == 0);	
		
	}
	
	
	}
	

