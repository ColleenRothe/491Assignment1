package Participation;
import org.junit.* ;
import static org.junit.Assert.* ;

/**
 * This is just a simple template for a JUnit test-class for testing 
 * the class Customer.
 */
public class Customer_test {

	@Test
	public void test1() {
		System.out.println("Provide here a short description of your test purpose here...") ;
		Customer C = new Customer(0,"Duffy Duck","") ;
		assertTrue(C.getCostToPay() == 0) ; 
	}
	
	@Test
	public void test2() { 
		System.out.println("Provide a short description...") ;
		// an example of test that fails, in this case trivially:
		assertTrue(1/0 == 0) ;
	}
	
	// and so on ...
}
