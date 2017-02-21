package Participation;

import java.io.Serializable;

/**
 * An instance of this class represents a unit of participation to
 * a certain service. A customer can buy participations.
 * 
 * See also the background provided in {@link Participation.ApplicationLogic}.
 */
public class Participation implements Serializable {

	/**
	 * The customer owning the participation.
	 */
	Customer customer ;
	
	/**
	 * The service to which the above customer participates.
	 */
	Service service ;
	
	
	/**
	 * Make a new Participation instance with the given id. It represents 
	 * a participation of the given customer to the given service.
	 */
	public Participation(Customer c, Service s) {
		customer = c ; service = s ; 
	}
		
	// Getters:
	
	/**
	 * Return the customer to which this participation belongs to.
	 */
	public Customer getCustomer(){ return customer ; }
	
	/**
	 * Return the service associated to this participation.
	 */
	public Service getService(){ return service ; }
	
}
