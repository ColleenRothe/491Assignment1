package Participation;

import static org.junit.Assert.*;

import org.junit.Test;

public class Discount_1000_test {

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
			//only applicable when customer has at least 1000 in participations
	
	
	//CALC DISCOUNT:
		//return discount value in euro cent
			//1 euro = 100 euro cents
		//gives 50 euro discount to every 1000 euro participations
	
	@Test
	//test an average working case to check if applicable
	//test average case to calculate the correct discount
	public void test1(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
		int donutID = SUT.addService("Granny's Donuts", 100000);
		
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID) ;
	
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_1000 D = new Discount_1000();
				
		//should be applicable and receive the discount
			//because involved in at least 1000 euro participations, twice
		assertTrue(D.applicable(C) == true);
		assertTrue(D.calcDiscount(C)==10000);

	}
	
	@Test
	//Test when participation value is -1 from getting the discount
	public void test2(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 99999) ;
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_1000 D = new Discount_1000();
		
		//should not be applicable, should receive no discount
		assertTrue(D.applicable(C) == false);
		assertTrue(D.calcDiscount(C)==0);
		
	}
	
	@Test
	//test when participation is +1 over threshold for one discount, only should get 1
	public void test3(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100001) ;
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_1000 D = new Discount_1000();
		
		//should be applicable, should receive 1 discount
		assertTrue(D.applicable(C) == true);
		assertTrue(D.calcDiscount(C)==5000);
		
	}
	
	@Test
	//test that 1000 euro participation value can be split between services
	public void test4(){
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 50000) ;
		int donutID = SUT.addService("Granny's Donuts", 50000);
		
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, donutID);
		
		Customer C = SUT.findCustomer(duffyID);
		Discount_1000 D = new Discount_1000();
		
		//should be applicable, should receive 1 discount
		assertTrue(D.applicable(C) == true);
		assertTrue(D.calcDiscount(C)==5000);
	}
	
}
