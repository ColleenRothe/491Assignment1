package Participation;

import java.io.Serializable;
import java.util.*;

/**
 * An object of this class represents a 'discount token'. A customer
 * may be awarded with discount tokens. Each token is only applicable
 * under a certain condition. At the end of the day, applicable tokens
 * will be applied, and consumed. 
 * 
 * <p>Note that this class is abstract. To implement a certain discount
 * scheme, we still need a concrete subclass implementing the scheme.
 * 
 * <p>See also the background provided in {@link Participation.ApplicationLogic}.
 */
public abstract class Discount implements Serializable {

	/**
	 * Return true if this discount token is applicable on the given customer.
	 */
	abstract public boolean applicable(Customer c) ;
	
	/**
	 * This methods returns the discount value, expressed in euro-cent. 
	 */
	abstract public int calcDiscount(Customer c) ;
	
	
	/**
	 * Return A textual description of this discount.
	 */
	abstract public String description() ;
	
}
