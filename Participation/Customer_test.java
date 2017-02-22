package Participation;
import org.junit.* ;

import Participation.Customer.ServiceInfo;

import static org.junit.Assert.* ;

import java.util.Map;
import java.util.Set;

/**
 * This is just a simple template for a JUnit test-class for testing 
 * the class Customer.
 */
public class Customer_test {
	
	/**
	 * Provide a functionality to reset the database. Here I will just
	 * delete the whole database file. 
	 */
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	//NEED TO TEST: GETDISCOUNTVALUE && GETCOSTTOPAY && GETPARTICIPATIONGROUPS && ___

	//GETDISCOUNTVALUE:
		//return total discount customer gets
		//cannot exceed their participation value
	
	//GETCOSTTOPAY:
			//returns net contribution cost (after discount) customer has to pay
	
//	@Test
//	//test that calculates discount correctly with only D5p on average case that works
//	public void test1(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 10000) ;
//		int donutID = SUT.addService("Granny's Donuts", 10000);
//		int pizzaServiceID = SUT.addService("Pizza delivery", 15000) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 12500);
//		int pharmacyID = SUT.addService("Pharmacy", 12500);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//		SUT.addParticipation(duffyID, pizzaServiceID) ;
//		SUT.addParticipation(duffyID, icecreamServiceID) ;
//		SUT.addParticipation(duffyID, pharmacyID) ;
//		
//		SUT.awardDiscount(duffyID, SUT.D5p);
//		Customer C = SUT.findCustomer(duffyID);
//
//		//should be discount val of 10 because 5 services, >100 each
//		assertTrue(C.getDiscountValue() == 10);
//		//test that getCostToPay calculates correctly with only D5p
//		assertTrue(C.getCostToPay() == 59990);
//	}
//	
//	@Test
//	//test with 5dp when there are only 4 services
//	public void test2(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 10000) ;
//		int donutID = SUT.addService("Granny's Donuts", 10000);
//		int pizzaServiceID = SUT.addService("Pizza delivery", 15000) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 12500);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//		SUT.addParticipation(duffyID, pizzaServiceID) ;
//		SUT.addParticipation(duffyID, icecreamServiceID) ;
//		
//		SUT.awardDiscount(duffyID, SUT.D5p);
//		Customer C = SUT.findCustomer(duffyID);
//		
//		//should be discount val of 0 because 4 services, >100 each
//		assertTrue(C.getDiscountValue() == 0);
//		//no discount, have to pay full
//		assertTrue(C.getCostToPay() == 47500);
//		
//	}
//	
//	@Test
//	//test with 5dp when there is a service <100
//	public void test3(){
//	setupDB() ;
//	ApplicationLogic SUT = new ApplicationLogic() ;
//	
//	int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//	int flowerServiceID = SUT.addService("Flowers online shop", 99) ;
//	int donutID = SUT.addService("Granny's Donuts", 10000);
//	int pizzaServiceID = SUT.addService("Pizza delivery", 15000) ;
//	int icecreamServiceID = SUT.addService("Ice Cream", 12500);
//	int pharmacyID = SUT.addService("Pharmacy", 12500);
//	
//	SUT.addParticipation(duffyID, flowerServiceID) ;
//	SUT.addParticipation(duffyID, donutID) ;
//	SUT.addParticipation(duffyID, pizzaServiceID) ;
//	SUT.addParticipation(duffyID, icecreamServiceID) ;
//	SUT.addParticipation(duffyID, pharmacyID) ;
//	
//	SUT.awardDiscount(duffyID, SUT.D5p);
//	Customer C = SUT.findCustomer(duffyID);
//	
//	//should be no discount because 1 service < 100
//	assertTrue(C.getDiscountValue() == 0);
//	//no discount, pay full amount
//	assertTrue(C.getCostToPay() == 50099);
//	}
//	
//	
//	@Test
//	//test with 1000 on average working case
//	public void test4(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
//		int donutID = SUT.addService("Granny's Donuts", 100000);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//	
//		SUT.awardDiscount(duffyID, SUT.D1000);
//		Customer C = SUT.findCustomer(duffyID);
//
//		//should be awarded discount 2x
//		assertTrue(C.getDiscountValue()==10000);
//		//test costToPay with correctly awarded 1000 discount
//		assertTrue(C.getCostToPay() == 190000);
//	}
//	
//	@Test
//	//test that can calculate discount mix of 5pack and 1000 correctly
//	public void test5(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
//		int donutID = SUT.addService("Granny's Donuts", 100000);
//		int pizzaServiceID = SUT.addService("Pizza delivery", 100000) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 100000);
//		int pharmacyID = SUT.addService("Pharmacy", 100000);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//		SUT.addParticipation(duffyID, pizzaServiceID) ;
//		SUT.addParticipation(duffyID, icecreamServiceID) ;
//		SUT.addParticipation(duffyID, pharmacyID) ;
//		
//		SUT.awardDiscount(duffyID, SUT.D5p);
//		SUT.awardDiscount(duffyID, SUT.D1000);
//		Customer C = SUT.findCustomer(duffyID);
//		
//		//should be able to get both types of discounts
//		assertTrue(C.getDiscountValue()==25010);
//		//cost to pay when applied both discounts
//		assertTrue(C.getCostToPay() == 474990);
//		
//	}
//	
//	@Test
//	//test that discount can't exceed participation value
//	public void test6(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
//		int donutID = SUT.addService("Granny's Donuts", 100);
//		int pizzaServiceID = SUT.addService("Pizza delivery", 100) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 100);
//		int pharmacyID = SUT.addService("Pharmacy", 100);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//		SUT.addParticipation(duffyID, pizzaServiceID) ;
//		SUT.addParticipation(duffyID, icecreamServiceID) ;
//		SUT.addParticipation(duffyID, pharmacyID) ;
//		
//		for(int i = 0; i<60; i++){
//		SUT.awardDiscount(duffyID, SUT.D5p);
//		}
//		
//		Customer C = SUT.findCustomer(duffyID);
//		
//		//applied more than participation value of discounts, so needs to be capped
//		assertTrue(C.getDiscountValue() == 500);
//		//should not have to pay anything, discounts = cost
//		assertTrue(C.getCostToPay() == 0);
//		
//	}
//	
//	
//	@Test
//	//test calculate contribution cost correctly, no discounts
//	public void test7(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
//		int donutID = SUT.addService("Granny's Donuts", 100);
//		int pizzaServiceID = SUT.addService("Pizza delivery", 100) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 100);
//		int pharmacyID = SUT.addService("Pharmacy", 100);
//		
//		SUT.addParticipation(duffyID, flowerServiceID) ;
//		SUT.addParticipation(duffyID, donutID) ;
//		SUT.addParticipation(duffyID, pizzaServiceID) ;
//		SUT.addParticipation(duffyID, icecreamServiceID) ;
//		SUT.addParticipation(duffyID, pharmacyID) ;
//		
//		Customer C = SUT.findCustomer(duffyID);
//		
//		//pay full amount, have no discounts
//		assertTrue(C.getCostToPay()==500);
//		//no discounts...discount value is 0
//		assertTrue(C.getDiscountValue() == 0);
//	}
//	
//	@Test
//	//test that getCostToPay works when there are no participations/cost 
//	public void test8(){
//		setupDB() ;
//		ApplicationLogic SUT = new ApplicationLogic() ;
//		
//		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
//		
//		Customer C = SUT.findCustomer(duffyID);
//		
//		assertTrue(C.getCostToPay() == 0);
//
//	}
	
	
	//GETPARTICIPATIONGROUPS:
		//returns participants, but grouped by services participated to
		//each group wrapped in ServiceInfo object
	
	@Test
	//test for multiple participations
	
	//come back to this one...doesn't always work
	public void test9(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		int donutID = SUT.addService("Granny's Donuts", 150);
		int pizzaServiceID = SUT.addService("Pizza delivery", 100) ;
		int flowerServiceID = SUT.addService("Flowers online shop", 200) ;
//		int icecreamServiceID = SUT.addService("Ice Cream", 100);
//		int pharmacyID = SUT.addService("Pharmacy", 100);
		
		SUT.addParticipation(duffyID, flowerServiceID);
		SUT.addParticipation(duffyID, flowerServiceID);
		SUT.addParticipation(duffyID, pizzaServiceID);
		SUT.addParticipation(duffyID, donutID);
		
		Customer C = SUT.findCustomer(duffyID);
		Map<Service,ServiceInfo> result  = C.getParticipationGroups();
		
		
		//check correct size
		assertTrue(C.getParticipationGroups().size() ==3);
		//check mapped correctly		
		//assertTrue(C.getParticipationGroups().keySet().iterator().next().name.equals("Pizza delivery"));
		//assertTrue(C.getParticipationGroups().keySet().iterator().next().price == 100);
		
		//test the participation value when involved in multiple services
		assertTrue(C.participationValue() == 650);

	}
	
	//PARTICIPATIONVALUE
			//Return the total participation value of this customer. 
			//This is his total contribution cost before discount.
	
	@Test
	//Test when no participations
	public void test10(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		Customer C = SUT.findCustomer(duffyID);
		
		//should both be 0 because no participations
		assertTrue(C.getParticipations().size() == 0);
		assertTrue(C.participationValue() == 0);
		
	}
	
	@Test
	//test that when discounts are applied, participationValue ignores them
	public void test11(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		
		int donutID = SUT.addService("Granny's Donuts", 100);
		int pizzaServiceID = SUT.addService("Pizza delivery", 100) ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		int icecreamServiceID = SUT.addService("Ice Cream", 100);
		int pharmacyID = SUT.addService("Pharmacy", 100);
		
		SUT.addParticipation(duffyID, flowerServiceID);
		SUT.addParticipation(duffyID, icecreamServiceID);
		SUT.addParticipation(duffyID, pizzaServiceID);
		SUT.addParticipation(duffyID, donutID);
		SUT.addParticipation(duffyID, pharmacyID);
		
		System.out.println(SUT.awardDiscount(duffyID, SUT.D5p));
		Customer C = SUT.findCustomer(duffyID);

		assertTrue(C.participationValue() == 500);
	}
	
	//come back, probs need to test more....
	

	
}
