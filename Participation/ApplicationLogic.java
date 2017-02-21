package Participation;

import java.util.*; 
import java.io.* ;

import com.db4o.* ;
import com.db4o.query.* ;
import com.db4o.config.*;

import static Participation.Persistence.* ;

/**
 * This class implements the logic of the "Foo's Participation System". 
 * 
 * <h4>Background</h4>
 *  
 * This Foo System is not a real business system; it is just a simulation
 * for your training purpose. It manages a set of <it> customers</it>, 
 * <it> services</it>, and customers' <it>participations</it> on 
 * services. It provides basic functionalities, e.g. to add and remove 
 * customers, services, etc.  A 'service' here represents some business, 
 * e.g. an online shop, or a pizza restaurant. The application provides 
 * a functionality to let a customer add/buy a participation to a service. 
 * In the real life this means that the customer would be charged for 
 * daily contribution to the services he participates in. In exchange he 
 * will get a proportional share of the services' profit. He can drop 
 * participation at any time. 
 * 
 * <p>Currently profit calculation is not included in the system, but 
 * it does calculate the contribution cost for each customer. Furthermore,
 * there is a functionality to award discount tokens to customers; these
 * give them reduction on the contribution cost they have to pay. The
 * application provides a functionality to 'resolve', intended to be
 * invoked by the application owner at the end of every day. It will
 * calculate the contribution cost of each customer, taking the discount
 * tokens into account.
 * 
 * The user interface to this Foo System is implemented by the class
 * {@link ParticipationSystem}. As said, this class implements the logic
 * part of the system.
 * 
 * <h4>About this class</h4>
 * 
 * The business data is stored in a database, using db4o OO-database. Access 
 * to this database goes by the way through the {@link Participation.Persistence} 
 * class. 
 * 
 * <p>This ApplicationLogic class offer functionalities such as to pull 
 * a customer from the database, to add a new one, or to remove an existing one. 
 * There are similar functionalities for services. We can also
 * add a participation to a customer, or delete it, and we can award a bonus token
 * to a customer, or remove it.
 * 
 * <p>Finally there is a method {@link #resolve()} that will apply all applicable 
 * bonus tokens, and calculate the contribution cost per customer. Those tokens 
 * are then consumed. 
 * 
 *
 */

public class ApplicationLogic implements Serializable {
	
	/**
	 * An inner class to keep track of fresh IDs for customer, service, 
	 * and participation. A fresh ID is needed when we add e.g. a new
	 * customer. He will need an ID.
	 * 
	 * <p>There will be 1x current Counter. Whenever we create 
	 * a customer, or a service, etc, this Counter will be consulted,
	 * and updated accordingly. We will also keep it persisted in the
	 * database.
	 */
	static class Counter {		
		
		/**
		 * Next fresh ID for customer.
		 */
		int nextFreeCustId = 1 ;
		
		/**
		 * Next fresh ID for service.
		 */
		int nextFreeServId = 1 ;
		
		/**
		 * Next fresh ID for participation.
		 */
		int nextFreePartId = 1 ;	
		
		/**
		 * Just a constructor; the internal counters will be initialized to
		 * 1.
		 */
		public Counter() { }
		
		/**
		 * Pull the current Counter from the database.
		 */
		static Counter getCounter() {
			return db.query(Counter.class).get(0) ;
		}
		
	}
		
	/**
	 * A constructor to create an instance of this class. It will also configure the 
	 * database. 
	 */
	public ApplicationLogic() {		
		configure() ;
		open() ;
		List<Counter> r = db.query(Counter.class) ;
		if (r.isEmpty()) db.store(new Counter()) ;
		close() ;
	}
	
	/**
	 * Return the list of all customers stored in the database. 
	 */
	public List<Customer> getCustomers() { 
		open() ;
		List<Customer> r = new LinkedList(db.query(Customer.class)) ;
		close() ;
		return r ;
	}

	/**
	 * Return the list of all services stored in the database.
	 */
	public List<Service> getServices() { 
		open() ;
		List<Service> r = new LinkedList(db.query(Service.class)) ;
		close() ;
		return r ;
	}

	/**
	 * Find a customer with the given ID in the database. It 
	 * returns the customer, or null if it can't be found.
	 */
	public Customer findCustomer(int custID) {	
		open() ;
		List<Customer> r = db.queryByExample(new Customer(custID,null,null)) ;
		if (r==null || r.isEmpty()) { close() ; return null ; }
		Customer c = r.get(0) ;
 		close() ;
		return c ;
	}
	
	/**
	 * Check if a customer with the given id exists in the database.
	 */
	public boolean custExists(int id) {
		return (findCustomer(id) != null) ;
	}
	
	/**
	 * Find a customer with the given name and email in the database.
	 * It returns the customer, or null if he cannot be found.
	 */
	public Customer findCustomer(String name, String email) {
		List<Customer> cs = getCustomers() ;
		for (Customer C : cs) {
			if (C.name.equals(name) && C.email.equals(email)) return C ;
		}
		return null ;
	}

	/**
	 * Find a service with the given ID in the database. It 
	 * returns the service, or null if it can't be found.
	 */
	public Service findService(int servID) {
		open() ;
		List<Service> r = db.queryByExample(new Service(servID,null,0)) ;
		if (r == null || r.isEmpty()) { close() ; return null ; }
		Service s = r.get(0) ;
		close() ;
		return s ;
	}
	
	/**
	 * Check if a service with the given id exists.
	 */
	public boolean serviceExists(int id){
		return (findService(id) != null) ;
	}

	
	/**
	 * Add a new customer with the given name and email. This succeeds if 
	 * no customer with the same combination of name and email already
	 * exists in the database. The new customer will get his ID, and this
	 * method will return this ID.
	 * 
	 * <p>If however there is already a customer in the database with the
	 * same combination of name and email, no new customer will be added,
	 * and this method will return -1.
	 */
	public int addCustomer(String name, String email) {

		open() ;
		Counter freshIds = Counter.getCounter() ;
		int id = freshIds.nextFreeCustId ;
		
		Customer c = new Customer(id,name,email) ;
		
		List<Customer> r = db.queryByExample(new Customer(0,name,email)) ;
		if (! r.isEmpty()) { close() ; return -1 ; }
		
		db.store(c) ;
		freshIds.nextFreeCustId++ ;
		db.store(freshIds) ;
		close() ;
		return id ;
	}

	
	/**
	 * Remove the customer of the given ID from the database. All his 
	 * participations will also be removed (cascade removal).
	 */
	public void removeCustomer(int custID) {
		open() ;
		List<Customer> cs= db.queryByExample(new Customer(custID,null,null)) ;		
		if (cs.isEmpty()) { close() ; return ; }	
		Customer c = cs.get(0) ;
		db.delete(c) ;
		for (Participation P : c.participations) db.delete(P) ;
		for (Discount D : c.discounts) db.delete(D) ;
		close() ;
	}
	
	/**
	 * Add a new service with the given name and price (in Euro-cent).
	 * It succeeds if no service with the same name already exists in
	 * the database. The new service is the added. It gets an ID, and
	 * this ID will be returned.
	 * 
	 * <p>If a service with the same name already exists, no new service
	 * will be added. This method then returns -1.
	 * 
	 * @param price Should be non-negative.
	 */
	public int addService(String name, int price) {
		
		assert price >= 0 : "PRE" ;
		
		open();
		Counter nextID = Counter.getCounter() ;
		int id = nextID.nextFreeServId ;
		Service s = new Service(id,name,price) ;
		List<Service> r = db.queryByExample(new Service(0,name,0)) ;
		
		if (! r.isEmpty()) { close() ; return -1 ; }
		
		db.store(s) ;
		nextID.nextFreeServId++ ;
		db.store(nextID) ;
		close() ;

		return id ;
	}
	
	
	/**
	 * Remove the service with the given ID. It should remove all participations
	 * to that service as well.
	 */
	public void removeService(int servID) {
				
		open() ;
		List<Service> ss= db.queryByExample(new Service(servID,null,0) ) ;
		
		if (ss.isEmpty()) { close() ; return ; }
		
		Service s = ss.get(0) ;
		db.delete(s) ;
		
		List<Customer> customers = db.query(Customer.class) ;
	
		for (Customer c : customers) {
			boolean changed = false ;
			List<Participation> tobeRemoved = new LinkedList<Participation>() ;
			for (Participation P : c.participations)
				if (P.service.ID == servID) {
					changed = true ;
					tobeRemoved.add(P) ;
					db.delete(P) ;
				} ;
			if (changed) {
				c.participations.removeAll(tobeRemoved) ;
				db.store(c) ;
			}
		}	
		
		close() ;
	}
	
	/**
	 * Add a new participation by the given customer for the given
	 * service. This is only possible if the IDs passed are valid
	 * IDs. The method then return true, else it returns false.
	 * 
	 */
	public boolean addParticipation(int custID, int servID) {	

		open();

		List<Customer> cs = db.queryByExample(new Customer(custID,null,null)) ;
		List<Service>  ss = db.queryByExample(new Service(servID,null,0)) ;
		
		if (cs.isEmpty() || ss.isEmpty()) {
			close(); 
			return false ;
		}
		
		Customer c = cs.get(0) ;
		Service s = ss.get(0) ;		
		Participation p   = new Participation(c,s) ;
		c.participations.add(p) ;
		db.store(c) ;
		close() ;
		return true ;
	}
	
	/**
	 * Just a string-code for {@link Participation.Discount_1000}.
	 */
	static public final String  D1000 = "D1000eur" ;
	
	/**
	 * A string-code for {@link Participation.Discount_5pack}.
	 */
	static public final String  D5p = "D5pack" ;
	
	
	private Discount mk_discount_token(String code) {
		if (code.equals(D1000)) return new Discount_1000() ;
		if (code.equals(D5p)) return new Discount_5pack() ;
		return null ;
	}
	
	/**
	 * Award a discount token to the given customer. It succeeds if the ID
	 * and the discount-code passed are valid ID/code. In that case it will
	 * return true, and else false.
	 */
	public boolean awardDiscount(int custID, String discountCode) {	
		Discount D = mk_discount_token(discountCode) ;
		if (D==null) return false ;
		open();
		List<Customer> cs = db.queryByExample(new Customer(custID,null,null)) ;
		if (cs.isEmpty()) { close(); return false ; }		
		Customer c = cs.get(0) ;
		c.discounts.add(D) ;
		db.store(D) ;
		db.store(c) ;
		close() ;
		return true ;
	}
	
	/**
	 * Remove a discount token to the given customer. It succeeds if the ID
	 * and the discount-code passed are valid ID/code. In that case it will
	 * return true, and else false.
	 */
	public boolean remDiscount(int custID, String discountCode) {	
		Discount D = mk_discount_token(discountCode) ;
		if (D==null) return false ;
		open();
		List<Customer> cs = db.queryByExample(new Customer(custID,null,null)) ;
		if (cs.isEmpty()) { close(); return false ; }		
		Customer c = cs.get(0) ;
		Discount found = null ;
		for (Discount E : c.discounts) {
			if (E.getClass().equals(D.getClass())) {
				found = E ;
				break ;
			}
		}
		if (found != null) c.discounts.remove(found) ;
		db.delete(found) ;
		db.store(c) ;
		close() ;
		return true ;
	}
	
	/**
	 * Will drop one participation of a customer on a service.
	 */
	public void dropParticipation(int custID, int servID) {		
		open() ;
		List<Customer> cs = db.queryByExample(new Customer(custID,null,null)) ;
		if (cs.isEmpty()) { close() ; return ; }
		
		Customer C = cs.get(0) ;
		
		Participation tobeDeleted = null ;
		for (Participation P : C.participations) {
			if (P.service.ID == servID) {
				tobeDeleted = P ;
				break ;
			}
		}
		if (tobeDeleted != null) {
			C.participations.remove(tobeDeleted) ;
			db.delete(tobeDeleted) ;
			db.store(C) ;
		}		
		close() ;		
	}
	
	/**
	 * Return the total paticipation's value (his total contribution cost 
	 * before discount) of the given customer. 
	 * Return -1 if the passed customer ID is not valid.
	 */
	public int participationValue(int custID) {
		Customer c = findCustomer(custID) ;
		if (c==null) return -1 ; 
		return c.participationValue() ;
	}
	
	/**
	 * Return the customer's total discount value. Return -1 if the customer ID
	 * is not valid.
	 */
	public int discount(int custID)  {
		Customer c = findCustomer(custID) ;
		if (c==null) return -1 ; 
		return c.getDiscountValue() ;
	}

	/**
	 * Return the customer's total contribution cost, after discount. Return -1 if the customer ID
	 * is not valid.
	 */
	public int costToPay(int custID)  {
		Customer c = findCustomer(custID) ;
		if (c==null) return -1 ; 
		return c.getCostToPay() ;
	}
	
	
	/**
	 * This is used to conclude a day trading. This calculates for each customer
	 * in the database, the contribution cost he has to pay, then consumes 
	 * all applicable discount tokens.
	 */
	public Map<Customer,Integer> resolve()  {
		open() ;
		Map<Customer,Integer> payment = new HashMap<Customer,Integer>() ;
		List<Customer> customers = db.query(Customer.class) ;
		for (Customer C: customers) {
			int topay = C.getCostToPay() ;
			List<Discount> applicable = new LinkedList<Discount>() ;
			for (Discount D : C.discounts) {
				if (D.applicable(C)) {
					db.delete(D) ;
					applicable.add(D) ;
				}
			}
			for (Discount D : applicable) C.discounts.remove(D) ;
			db.store(C) ;
			payment.put(C,topay) ;
 		}
		close() ;
		return payment ;
	}
	
	/*  Ignore this; for dev test only.
	static public void main(String[] args) {
		Persistence.DEBUG = true ;
		ApplicationLogic app = new ApplicationLogic() ;
		System.out.println("## " + app.getCustomers().size() + " customers.") ;
		app.addCustomer("Woosh","woosh@google") ;
		app.addCustomer("Otje","otje@google") ;
		List<Customer> cs = app.getCustomers() ;
		System.out.println("## " + cs.size() + " customers.") ;
		for (Customer c : cs) {
			System.out.println("CUST " + c.name + " subscribes " + c.participations.size()) ;
		}
		System.out.println("## rolling back...") ;
		resetdb() ;
		System.out.println("## " + app.getCustomers().size() + " customers.") ;
	}
	*/
	
}
