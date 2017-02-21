package Participation;

import java.io.Serializable;
import java.util.*;

/**
 * This discount token gives 50 euro discount to every 1000 euro
 * participations.
 * 
 * <p>See also {@link Participation.Discount}.
 */
public class Discount_1000 extends Discount implements Serializable {

	static public final String DESC
		= 
		"50 eur. discount for each 1000 euro participation-value." ;
	
	public Discount_1000() { }	
	
	@Override
	public String description() { return DESC ; }
	
	/**
	 * {@inheritDoc} 
	 * This discount token in only applicable when the customer has at least
	 * 1000 euro participation value.
	 */
	@Override
	public boolean applicable(Customer c) {
		return c.participationValue() >= 1000 * 100 ;
	}

	/**
	 * {@inheritDoc}
	 * This token gives 50 euro discount to every 1000 euro participations.
	 */
	@Override
	public int calcDiscount(Customer c) {
		return 50 * 100 * (c.participationValue() / (1000 * 100)) ;
	}

}
