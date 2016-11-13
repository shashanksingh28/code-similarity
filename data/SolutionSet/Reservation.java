// Assignment #: 8
//         Name: Daniel D'Souza
//    StudentID: 1207035111
//  Lab Lecture: Tu/Thrs 4:30
//  Description: encapsulated reservation class

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Reservation implements Serializable {

	private ArrayList<Comparable> passengerList; //holds list of passengers objects
	private ArrayList<Comparable> flightList;// holds list of flight objects
	
	/**
	 * Constructs a new Reservation
	 */
	public Reservation() {
		passengerList = new ArrayList<Comparable>();
		flightList = new ArrayList<Comparable>();
	}
	
	/**
	 * Checks if a passenger has a reservation based on passenger ID
	 * @param s, The ID of a passenger as a String
	 * @return the index of the passenger, or -1 if not found
	 */
	public int passengerExists(String s) {
		for (int p=0; p< passengerList.size(); p++) {
			Passenger a = (Passenger) passengerList.get(p);
			if (a.getPassengerID().equalsIgnoreCase(s))
				return p;
		}
		return -1;
	}
	
	/**
	 * Adds a passenger, if the passenger is not a duplicate
	 * @param s, The ID of the passenger as a String
	 * @return true if was removed, false if not
	 */
	public boolean addPassenger(String s) {
		Passenger p = PassengerParser.parseStringToPassenger(s); //creates passenger object
		if (passengerExists(p.getPassengerID()) == -1) {
			passengerList.add(p);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a passenger, if the passenger exists
	 * @param s, The ID of the passenger to remove
	 * @return true if was removed, false if not
	 */
	public boolean removePassenger(String s){
		for (Comparable c : passengerList) {
			Passenger p = (Passenger) c;
			if (p.getPassengerID().equalsIgnoreCase(s)) {
				passengerList.remove(c);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates a new sorter, updates the passengerList with a sorted list
	 */
	public void sortPassengers() {
		Sorts s = new Sorts(passengerList);
		passengerList = s.getSortedList();
	}
	
	/**
	 * Outputs the list of passengers to the console
	 * @return the string of all passengers
	 */
	public String listPassengers() {
		String result = "";
		if (passengerList.size() == 0)
			result = "no passenger\n";
		else {
			for (Comparable c: passengerList){
				Passenger p = (Passenger) c;
				result += p.toString();
			}
		}
		return result;
	}
	
	/**
	 * Checks if a flight exist or not
	 * @param airlinesName, the company name as a String
	 * @param flightNum, the flight number as a String
	 * @param depCity, the departure City as a String
	 * @return the index of the flight, or -1 if the flight does not exist
	 */
	public int flightExists(String airlinesName, String flightNum, String depCity) {
		for (int p=0; p< flightList.size(); p++) {
			Flight f = (Flight) flightList.get(p);
			if (f.getAirlines().equalsIgnoreCase(airlinesName) &&
					f.getFlightNum().equalsIgnoreCase(flightNum) &&
					f.getDeparture().getCity().equalsIgnoreCase(depCity))
				return p;
		}
		return -1;
	}
	
	/**
	 * Add a flight, provided a duplicate does not exist
	 * @param s, String with information to pass to parser
	 * @return true if flight was added, false if not
	 */
	boolean addFlight(String s) {
		Flight p = FlightParser.parseStringToFlight(s); //creates flight object
		if (flightExists(p.getAirlines(), p.getFlightNum(), p.getDeparture().getCity()) == -1) {
			flightList.add(p);
			//sortFlights();
			return true;
		}
		return false;		
	}
	
	/**
	 * Removes a flight, provided the flight exists
	 * @param airlinesName
	 * @param flightNum
	 * @param depCity
	 * @return true if flight was remvoed, false if not
	 */
	boolean removeFlight(String airlinesName, String flightNum, String depCity){
		for (Comparable c : flightList) {
			Flight f = (Flight) c;
			if (f.getAirlines().equalsIgnoreCase(airlinesName) &&
					f.getFlightNum().equalsIgnoreCase(flightNum) &&
					f.getDeparture().getCity().equalsIgnoreCase(depCity)) {
				flightList.remove(c);
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Creates a new sort object, and updates flightList with the sorted list
	 */
	public void sortFlights() {
		Sorts s = new Sorts(flightList);
		flightList = s.getSortedList();		
	}
	
	/**
	 * List all flights
	 * @return A string with the list
	 */
	public String listFlights() {
		String result = "";
		if (flightList.size() == 0)
			result = "no flight\n";
		else {
			for (Comparable c: flightList) {
				Flight f = (Flight) c;
				result += f.toString();	
			}
		}
		return result;		
	}
	
	/**
	 * Closes the reservation by emptying the lists
	 */
	public void closeReservation() {
		passengerList.clear();
		flightList.clear();
	}
}
