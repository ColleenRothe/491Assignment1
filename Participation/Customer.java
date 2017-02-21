package Participation;

import java.io.Serializable;
import java.util.* ;

/**
 * An instance of this class represents a customer. 
 * 
 * See also the background provided in {@link Participation.ApplicationLogic}.
 */
public class Customer implements Serializable {
	
	int ID ;
	String name ;
	String email  ;
	
	/**
	 * All participations that this customer owns.
	 */
	Set<Participation> participations = new HashSet<Participation>() ;

	/**
	 * Discount tokens that this customer owns.
	 */
	Set<Discount> discounts = new HashSet<Discount>() ;

	
	/**
	 * Create a Customer object with the given ID, name, and email.
	 */
	public Customer(int id, String myName, String myEmail) {
		ID = id ;
		name = myName ;
		email = myEmail ;
	}
	
	/**
	 * Create a Customer object with the given name, and email.
	 * ID is set to a non-valid -1.
	 */
	public Customer(String myName, String myEmail) {
		ID = -1 ;
		name = myName ;
		email = myEmail ;
	}
	
	// Bunch of getters.
	
	/**
	 * Returns this customer's id.
	 */
	public int getID() { return ID ; }
	
	/**
	 * Return this customer's name.
	 */
	public String getName() { return name ; }
	
	/**
	 * Return this customer's email.
	 */
	public String getEmail() { return email ; }
	
	/**
	 * Return the set of participations belonging to this customer.
	 */
	public Set<Participation> getParticipations() { return participations ; }

	/**
	 * Return the total participation value of this customer. This is his total contribution 
	 * cost before discount.
	 */
	public int participationValue() {
		int val = 0 ;
		for (Participation P : participations) val += P.service.price ;
		return val ;
	}
	
	/**
	 * This inner class is just a helper. Objects of this class are 
	 * used to bundle service-related information together for the 
	 * purpose of passing the information from some methods to another
	 * in a single bundle.
	 */
	static public class ServiceInfo {
		public int totalParticipationValue = 0 ;
		public Set<Participation> participations = new HashSet<Participation>() ;
	}
	
	/**
	 * Return the participations, but grouped by the services participated to.
	 * Each group will be wrapped in an a ServiceInfo object.
	 */
	public Map<Service,ServiceInfo> getParticipationGroups() { 
		
		Map<Service,ServiceInfo> result = new HashMap<Service,ServiceInfo>() ;
		
		for (Participation P : participations) {
			ServiceInfo info = result.get(P.service) ;
			if (info==null) {
				info = new ServiceInfo() ;
				result.put(P.service,info) ;
			}
			info.totalParticipationValue += P.service.price ;
			info.participations.add(P) ;
		}
		return result ; 
	}
	
	/**
	 * Return the set of discount token belonging to this customer.
	 */
	public Set<Discount> getDiscounts() { return discounts ; }
	
	/**
	 * Return the total discount this customer gets. It cannot exceed his
	 * participation value.
	 */
	public int getDiscountValue() {
		int discount = 0 ;
		for (Discount D : discounts) {
			if (D.applicable(this)) discount += D.calcDiscount(this) ;
		}
		return Math.min(participationValue(),discount) ;
	}
	
	/**
	 * Return the net contribution cost (after discount) the customer has to pay.
	 */
	public int getCostToPay() {
		return participationValue() - getDiscountValue() ;
	}
	
	/**
	 * Return the set of all services this customer participates.
	 */
	public Set<Service> getServices() {
		return getParticipationGroups().keySet() ;
	}
	

}
