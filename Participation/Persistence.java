package Participation;

import java.io.*;
import java.util.*;

import Participation.ApplicationLogic.Counter;

import com.db4o.* ;
import com.db4o.config.Configuration;


/**
 * This class provides the link to the database, which is used to store
 * the data of the Foo System. See also the background provided in {@link Participation.ApplicationLogic}.
 * 
 * <p>This class specifies the file the database engine uses to store 
 * the data. It provides functionalities to open and close the database.
 * 
 * <p>For testing purposes there are also methods for reseting the database,
 * and to wipe the data file.
 * 
 */
public class Persistence {

	/**
	 * This specifies the file used by the database engine to store the data.
	 */
	//  Note for testing: set it to RAMdisk for faster access.
	public static final String dbfile = "foodata" ;
	
	// dbfile for normal operation
	// public static final String dbfile = "foodata" ;
	
	static ObjectContainer db  ;
	
	static Configuration conf ;

	public static boolean DEBUG = false ;

	/**
	 * This will configure the database engine, making it ready to access the 
	 * data file.
	 */
	static void configure()  {
		// Configuring the database:
		conf = Db4o.configure() ;
		conf.objectClass(Customer.class).cascadeOnUpdate(true) ;
		//conf.objectClass(Customer.class).minimumActivationDepth(10) ;
		// shound NOT cascade on delete!! Else you'll delete the services as well.
		// conf.objectClass(Customer.class).cascadeOnDelete(true) ;
		if (DEBUG) {
			if (db!=null) db.close() ;
			db = Db4o.openFile(conf,dbfile) ;
		}
	}

	/**
	 * This opens the database. When opened, the rest of Foo System can interact with it.
	 */
	static void open() {
		if (DEBUG) return ;
		db = Db4o.openFile(conf,dbfile) ;
	}
	
	/**
	 * This closes the database.
	 */
	static void close() {
		if (DEBUG) return ;
		db.close() ;
	}
	
	/**
	 * This method is provided to support testing. It should delete all objects from the
	 * database, thus restoring it to the empty state. This is to avoid that test cases
	 * interfere with each other.
	 */
	static void resetdb() {
		if (DEBUG)  {
			if (db != null) db.rollback() ;
			List<Customer> cs = new LinkedList(db.query(Customer.class)) ;
			List<Service> ss = new LinkedList(db.query(Service.class)) ;
			List<ApplicationLogic.Counter> cnts = new LinkedList(db.query(ApplicationLogic.Counter.class)) ;
			for (Customer C : cs) db.delete(C) ;
			for (Service S : ss) db.delete(S) ;
			for (ApplicationLogic.Counter c : cnts) db.delete(c) ;
			db.commit() ;
		}
	}
	
	/**
	 * This method is provided to support testing. This wipes out the data file, thus
	 * reseting the database to the empty state. This is to avoid that test cases
	 * interfere with each other. This is an alternative to {@link #resetdb()}.
	 */
	static void wipedb() {
		(new File(dbfile)) . delete() ;
	}
		
}
