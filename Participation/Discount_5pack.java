package Participation;

import java.util.Map;
import java.util.Set;

import Participation.Customer.ServiceInfo;

/**
 * This discount token gives a 10 euro discount, if the customer
 * participates in at least 5 services, each with value at
 * least 100 euro.
 * 
 * <p>See also {@link Participation.Discount}.
 */
public class Discount_5pack extends Discount {

	public static final String DESC = 
		"10 eur. discount if the customer participates in at least 5 services, "
		+ "each with value at least 100 eur." ;

	public Discount_5pack() { }
	
	@Override
	public String description() { return DESC ; }
	
	/**
	 * {@inheritDoc} 
	 * This token is applicable on the customer if he owns participations in
	 * at least 5 services, each with value at least 100 euro.
	 */
	@Override
	public boolean applicable(Customer c) {
		
		Map<Service,Customer.ServiceInfo> ps = c.getParticipationGroups() ;
		
		if (ps.keySet().size() < 5) return false ;
		int count = 0 ;
		for (Service S : ps.keySet()) {
			if (ps.get(S).totalParticipationValue >= 100) count++ ;
		}
		return count >= 5 ;	
	}
	
	// inject
	
	/**
	 * {@inheritDoc} 
	 * This token gives 10 euro discount.
	 */ 
	@Override
	public int calcDiscount(Customer c) { return 10 ; }

}
