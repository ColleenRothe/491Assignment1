import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;   
import java.util.*; 

import Participation.*;

/**
 * This class provides a minimalistic user interface to the Foo System.
 * This logic is implemented by the class {@link Participation.ApplicationLogic}.
 *
 */
public class ParticipationSystem extends JFrame 
	
	implements ActionListener, KeyListener
	
	{
	
	/**
	 * This variable holds the underlying business 
	 * logic.
	 */
	private ApplicationLogic appLogic  ;
	
	// Variables holding GUI components:
	
	private static int TEXTWIDTH = 80 ;
	
	private JTextArea  textArea = new JTextArea (TEXTWIDTH,40);  
	private JScrollPane scrollpane = new JScrollPane(textArea);
	private JTextField commandField = new JTextField(TEXTWIDTH) ;
	
	private java.util.List<String> commandHistory = new LinkedList<String>() ;
	private int historyCursor = 0 ;
	
	// Just a constructor:
	
	public ParticipationSystem() {
		
		appLogic = new ApplicationLogic() ;
		
		// Setting the look:
		
		setSize(550, 500); 
	    setTitle("Foo's Participation System --version 0"); 
	    setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close operation (exit when it gets closed)  
	    textArea.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 20)) ;
	    commandField.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 20)) ;
	    textArea.setBackground(Color.BLACK) ;
	    textArea.setForeground(Color.WHITE) ;
	    textArea.setText("This is NOT a production system. This system is for training.\n"
	    		       + "Despite being minimalistic, it has the typical architecture\n"
	    		       + "of a business application: it has an application logic layer,\n"
	    		       + "with a persistence layer underneath, and a user interface layer\n"
	    		       + "on top of it. Enjoy.\n\n"
	    		       + "Author: wishnu@cs.uu.nl\n\n"
	    		       + "Type help to list available commands." ) ;
	    	   
	    // Plugging GUI components:
	    Container contentPane = getContentPane() ;
	    contentPane.setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically  
	    contentPane.add(scrollpane,BorderLayout.CENTER);  
	    contentPane.add(commandField,BorderLayout.SOUTH) ;
	    
	    textArea.setEditable(false) ;
   
	    commandField.addActionListener(this) ;
	    commandField.addKeyListener(this) ;
	    
	}
		
	/**
	 * This reacts to the command typed in the command box.
	 */
	//@Override
	public void actionPerformed(ActionEvent event) {
		// Calling a command interpreter here:
		commandInterpreter(commandField.getText()) ;
		commandField.setText("") ;
		commandField.repaint();
	}
	
	static private final String CLEAR = "clear" ;
	static private final String HELP = "help" ;
	static private final String ADD = "add" ;
	static private final String REMOVE = "rem" ;
	static private final String SHOW = "show" ;
	static private final String EXEC = "exec" ;
	static private final String CUST = "c" ;
	static private final String SERVICE = "s" ;
	static private final String PARTICIPATION = "p" ;
	static private final String DISCOUNT = "d" ;
	static private final String DISCOUNT_1000 = ApplicationLogic.D1000 ;
	static private final String DISCOUNT_5p = ApplicationLogic.D5p ;

	/**
	 * This interpret the commands, and calls the right functions 
	 * based on the given command. It also handles error messaging.
	 */
	private void commandInterpreter(String input) {
		
		commandHistory.add(input) ;
		historyCursor=0 ;
		
		String[] args = input.split(":") ;
		if (args.length==0) return ;
		
		String command = args[0] ;
		
		if (command.equals(CLEAR)) { clear() ; return ; }
		
		echo("> " + input) ;

		if (command.equals(HELP))  { help() ; return ; }
		
		if (command.equals(SHOW)) {
			
			if (args.length==1) { echo("(!) Show what?") ; return ; }
			if (args.length==2) { echo("(!) Specify target.") ; return ; }
			
			if (args[1].equals(CUST)) {
				if (args[2].equals("*")) { showAllCustomers() ; return ; }
				showCustomer(args[2]) ;
				return ;
			}
			if (args[1].equals(SERVICE)) {
				showAllServices() ; return ; 
			}
			echo(UNKNOWN_SELECTOR) ; return ;
		}
		else if(command.equals(ADD)) {
			if (args.length==1) { echo("(!) Add what?") ; return ; }
			if (args[1].equals(CUST)) {
				if (args.length<4) { echo("(!) Specify name AND email.") ; return ; }
				addCustomer(args[2],args[3]) ;
				return ;
			}
			if (args[1].equals(SERVICE)) {
				if (args.length<4) { echo("(!) Specify name AND price.") ; return ; }
				addService(args[2],args[3]) ;
				return ;
			}
			if (args[1].equals(PARTICIPATION)) {
				if (args.length<4) { echo("(!) Specify customer AND service ID.") ; return ; }
				addParticipation(args[2],args[3]) ;
				return ;
			}
			if (args[1].equals(DISCOUNT)) {
				if (args.length<4) { echo("(!) Specify customer AND discount code.") ; return ; }
				awardDiscount(args[2],args[3]) ;
				return ;
			}
			echo(UNKNOWN_SELECTOR) ; return ;
		}
		else if(command.equals(REMOVE)) {
			if (args.length==1) { echo("(!) Remove what?") ; return ; }
			if (args.length==2) { echo("(!) Specify removal target.") ; return ; }
			
			if (args[1].equals(CUST)) {
				removeCustomer(args[2]) ;
				return ;
			}
			if (args[1].equals(SERVICE)) {
				removeService(args[2]) ;
				return ;
			}
			if (args[1].equals(PARTICIPATION)) {
				if (args.length<4) { echo("(!) Specify service ID.") ; return ; }
				dropParticipation(args[2],args[3]) ;
				return ;
			}			
			if (args[1].equals(DISCOUNT)) {
				if (args.length<4) { echo("(!) Specify customer AND discount code.") ; return ; }
				removeDiscount(args[2],args[3]) ;
				return ;
			}
			echo(UNKNOWN_SELECTOR) ; return ;
		}
		
		if (command.equals(EXEC))  { 
			resolve() ;
			return ; 
		}

		
		echo(UNKNOWN_COMMAND) ;
	}
	
	//
	// These are the functions that implement various commands.
	//
	
	private void echo(String s) {
		String txt = textArea.getText() ;
		txt = txt + "\n" + s  ;
		textArea.setText(txt) ;
		textArea.repaint()  ;
	}
	
	/**
	 * Implementing the command to clear the screen.
	 */
	private void clear(){
		textArea.setText("") ;
		textArea.repaint()  ;
	}

	// Helper
	private String padRight(String s, int width) {
		if (s.length()>=width) return s.substring(0,width) ;
		String t = "" + s ;
		for (int i=t.length(); i<width; i++) t = t + " " ;
		return t ;		
	}
	
	// Helper
	private String padLeft(String s, int width) {
		if (s.length()>=width) return s.substring(0,width) ;
		String t = "" + s ;
		for (int i=t.length(); i<width; i++) t = " " + t ;
		return t ;		
	}
	
	// Helper
	private float cent2euro(int cent) { return (float) cent / 100f	; }
	
	static private final String COLLON = " : " ;
	static private final String CLN = ":" ;
	static private final String COMMA = " , "  ;
	static private final String TAB = "   " ;
	static private final String DONE = "Done" ;
	static private final String ARG_INCOMPL = "(!) Some argument(s) missing." ;
	static private final String UNKNOWN_SELECTOR = "(!) Unknown selector." ;
	static private final String UNKNOWN_COMMAND  = "(!) Unknown command." ;
	static private final String PASS_PINT = "(!) A positive integer is needed." ;
	
	/**
	 * Implementing the command for printing help to the screen.
	 */
	private void help() {
		String out = "\n"
			+ "COMMANDS:\n\n"
			+ TAB + "help\n"
			+ TAB + CLEAR + "\n"
			+ TAB + SHOW + CLN + "(c|s)" + CLN + "(*|id)" + "\n"
			+ TAB + REMOVE + CLN + "(c|s)" + CLN + "id" + "\n"
			+ TAB + REMOVE + CLN + "p" + CLN + "customerId" + CLN + "serviceId\n"
			+ TAB + ADD + CLN + "c" + CLN + "name" + CLN + "email" + "\n"
			+ TAB + ADD + CLN + "s" + CLN + "name" + CLN + "daily contribution cost (in euro-cent)" + "\n"
			+ TAB + ADD + CLN + "p" + CLN + "customerId" + CLN + "serviceId\n" 
			+ TAB + EXEC + "\n"
			+ "\n" + TAB + "Just close the window to quit. Your data is persisted.\n" ;
		echo(out) ;
	}
	
	/**
	 * Implementing the command for showing all customers.
	 */
	private void showAllCustomers(){
		java.util.List<Customer> customers = appLogic.getCustomers() ;
		String out = "ALL CUSTOMERS: (" + customers.size() + ")\n" ;
		for (Customer c : customers) 
			out = out 
				+ padRight("" + c.getID(), 3)
			    + COLLON
			    + padRight(c.getName(),16)
			    + COLLON 
			    + padRight(c.getEmail(),24)
			    + COLLON
			    + padRight("" + cent2euro(c.participationValue()),10)
			    + "\n" ;			    
		echo(out) ;
	}
	
	/**
	 * Implementing the command for showing the data of a particular customer.
	 */
	private void showCustomer(String custID){
		Integer id_ ;
		id_ = parseInt(custID) ;
		if (id_ == null  ||  id_<0) { echo(PASS_PINT) ; return ; }
		
		Customer c = appLogic.findCustomer(id_) ;
		
		if (c==null) { echo("This customer does not exists.") ; return ; }		
	
		String out = c.getName() + " (ID " + c.getID() + ")\n" ;
		out += "Email: " + c.getEmail() + "\n";
		out += "Total: " + c.getParticipations().size() + " participations, value; " 
			+ cent2euro(c.participationValue())  + " euro\n" ;
		out += "Participations:\n" ;
		Map<Service,Customer.ServiceInfo> M = c.getParticipationGroups() ;
		for (Service S : M.keySet() ) {
			int n = M.get(S).participations.size() ;
			int value = n * S.getPrice() ;
			out += TAB
				+ "Sevice " + S.getID() + ", " 
				+  n + " participations, value: "
				+  cent2euro(value) + " euro\n" ;
		}
		out += "Discounts:\n" ;
		for (Discount D : c.getDiscounts()) {
			out += TAB
				+ D.getClass().getSimpleName() ;
			if (D.applicable(c)) {
				out += ", applicable, value: " 
					+ cent2euro(D.calcDiscount(c)) + " euro" ;
			}
			out += "\n" ;
		}
		echo(out) ;
	}

	/**
	 * Implementing the command for showing all services.
	 */
	private void showAllServices(){
		java.util.List<Service> services = appLogic.getServices() ;
		String out = "ALL SERVICES: (" + services.size() + ")\n" ;
		for (Service s : services) 
			out = out 
				+ padRight("" + s.getID(), 3)
			    + COLLON
			    + padRight(s.getName(),24)
			    + COLLON 
			    + padLeft("" + s.getPrice(),10)
			    + "\n" ;		    
		echo(out) ;
	}
	
	/**
	 * Implementing the command for adding a new customer.
	 */
	private void addCustomer(String name, String email) {
		int r = appLogic.addCustomer(name,email) ;
		if (r<0) echo("(!) This customer already exists.") ;
		else echo(DONE) ;
	}

	// Helper
	static private Integer parseInt(String  i) {
		Integer x = null ;
		try { x = Integer.parseInt(i) ; }
		catch(Exception e) { } ;
		return x ;
	}
	
	/**
	 * Implementing a command to remove a customer.
	 */
	private void removeCustomer(String id) {
		Integer id_ ;
		id_ = parseInt(id) ;
		if (id_ == null  ||  id_<0) { echo(PASS_PINT) ; return ; }
		if (!appLogic.custExists(id_)) { echo("(!) This customer does not exists.") ; return ; }
		
		appLogic.removeCustomer(id_) ;
		echo(DONE) ;		
	}
	
	/**
	 * Implementing a command to add a new service. Price is the daily contribution cost of
	 * that service in euro-cent.
	 */
	private void addService(String name, String price) {
		Integer pr = parseInt(price) ;
		if (pr==null || pr<0) { echo(PASS_PINT) ; return ; }
		int r = appLogic.addService(name,pr) ;
		if (r<0) echo("(!) This service already exists.") ;
		else echo(DONE) ;
	}
	
	/**
	 * Implementing a command to remove a service.
	 */
	private void removeService(String id) {
		Integer id_ ;
		id_ = parseInt(id) ;
		if (id_ == null  ||  id_<0) { echo(PASS_PINT) ; return ; }
		if (!appLogic.serviceExists(id_)) { echo("(!) This service does not exists.") ; return ; }
		 
		appLogic.removeService(id_) ;
		echo(DONE) ;		
	}

	/**
	 * Implementing a command to add a new participation. The arguments are
	 * the id of the customer that gets the participation, and the id of
	 * the service he participates to.
	 */
	private void addParticipation(String custID, String serviceID){
		Integer cid_ = parseInt(custID) ;
		Integer sid_ = parseInt(serviceID) ;
		if (cid_ == null  || sid_ == null
				|| cid_ < 0
				|| sid_ < 0 ) { echo(PASS_PINT) ; return ; }
		if (!appLogic.custExists(cid_)) { echo("(!) The customer does not exists.") ; return ; }
		if (!appLogic.serviceExists(sid_)) { echo("(!) The service does not exists.") ; return ; }
		appLogic.addParticipation(cid_, sid_) ;
		echo(DONE) ;

	}
	
	/**
	 * Implementing a command to drop a single participation.
	 */
	private void dropParticipation(String custID, String serviceID){
		Integer cid_ = parseInt(custID) ;
		Integer sid_ = parseInt(serviceID) ;
		if (cid_ == null  || sid_ == null
				|| cid_ < 0
				|| sid_ < 0 ) { echo(PASS_PINT) ; return ; }
		if (!appLogic.custExists(cid_)) { echo("(!) The customer does not exists.") ; return ; }
		if (!appLogic.serviceExists(sid_)) { echo("(!) The service does not exists.") ; return ; }
		
		appLogic.dropParticipation(cid_,sid_) ;
		echo(DONE) ;

	}
	
	private void awardDiscount(String custID, String discountcode) {
		Integer cid_ = parseInt(custID) ;
		if (cid_ == null  || cid_ < 0) { echo(PASS_PINT) ; return ; }
		if (!appLogic.custExists(cid_)) { echo("(!) The customer does not exists.") ; return ; }
		if (!discountcode.equals(DISCOUNT_1000) && !discountcode.equals(DISCOUNT_5p)) {
			echo("(!) Not a valid discount code.") ; return ;
		}
		appLogic.awardDiscount(cid_, discountcode) ;
		echo(DONE) ;
	}
	
	
	private void removeDiscount(String custID, String discountcode) {
		Integer cid_ = parseInt(custID) ;
		if (cid_ == null  || cid_ < 0) { echo(PASS_PINT) ; return ; }
		if (!appLogic.custExists(cid_)) { echo("(!) The customer does not exists.") ; return ; }
		if (!discountcode.equals(DISCOUNT_1000) && !discountcode.equals(DISCOUNT_5p)) {
			echo("(!) Not a valid discount code.") ; return ;
		}
		appLogic.remDiscount(cid_, discountcode) ;
		echo(DONE) ;
	}
	
	private void resolve() {
		Map<Customer,Integer> M = appLogic.resolve() ;
		String out = "Resolving participations....\n" ;
		for (Customer C : M.keySet()) {
			out += TAB + C.getName() + " pays " + cent2euro(M.get(C)) + " euro\n" ;
		}
		echo(out) ;
	}
	/**
	 * React to arrow-up and arrow-down in the command box to scroll
	 * through commands history. (Yes we have that too! :)
	 */
	//@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_UP) {
			if (commandHistory.size()==0) return ;
			if (historyCursor==0) historyCursor = commandHistory.size()-1 ;
			else historyCursor-- ;
			commandField.setText(commandHistory.get(historyCursor)) ;
			commandField.repaint() ;
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			if (commandHistory.size()==0) return ;
			if (historyCursor>=commandHistory.size()-1) historyCursor = 0 ;
			else historyCursor++ ;
			commandField.setText(commandHistory.get(historyCursor)) ;
			commandField.repaint() ;
		}
	}

	//@Override
	public void keyReleased(KeyEvent e) { }

	//@Override
	public void keyTyped(KeyEvent e) { }

	
	/**
	 * The main method that will launch this application.
	 */
	static public void main(String[] args) {
		//Persistence.DEBUG = true ;
		ParticipationSystem sys = new ParticipationSystem() ;
		sys.setVisible(true) ;
	}

}
