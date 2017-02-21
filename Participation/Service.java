package Participation;

import java.io.Serializable;

/**
 * An instance of this class represents some busines, e.g. an online shop.
 * Customers can buy participations to a service. This means that he will 
 * contribute some money to pay for the operation of the service, and in 
 * return he will share the profit the service gets. See also the background
 * provided in {@link Participation.ApplicationLogic}.
  */
public class Service implements Serializable {

	int ID ;
	String name ;
	
	/**
	 * The daily price (contribution cost) of the service in euro-cent. 
	 */
	int price ;
	
	/**
	 * Make a new Service instance with the given id, name, and daily
	 * price (in euro-cent).
	 * 
	 * @param servicePrice Should be non-negative.
	 */
	public Service(int id, String serviceName, int servicePrice) {
		ID = id ; name = serviceName ; price = servicePrice ;
	}
	
	// Bunch of getters:
	
	/**
	 * Return this service's id.
	 */
	public int getID() { return ID ; }
	
	/**
	 * Return this service's price in euro-cent.
	 */
	public int getPrice() { return price ; }
	
	/**
	 * Return this service's name.
	 */
	public String getName() { return name ; }
	
}
