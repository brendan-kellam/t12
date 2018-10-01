package ca.mcgill.ecse321.webservice.model;

import javax.persistence.Entity;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Trip{

	private Long id; 
	private String startpoint;
	private String endpoint;
	private int distance;
	private boolean active;
	private Time start_time;
	private Time end_time;
	private int est_Trip_time;
	private int seats_available;
	private boolean compleated;
	private Set<Registration> registrations = new HashSet<>();
	private Set<TripNode> tripNodes = new HashSet<>();
	private Vehicle vehicle;
	
	
	public Trip() {
		
	}
	
	public Trip(
			String startpoint, 
			String endpoint, 
			int distance,
			boolean active, 
			Time start_time, 
			Time end_time, 
			int est_Trip_time,
			int seats_available, 
			boolean compleated,
			Vehicle vehicle) {
		this.startpoint = startpoint;
		this.endpoint = endpoint;
		this.distance = distance;
		this.active = active;
		this.start_time = start_time;
		this.end_time = end_time;
		this.est_Trip_time = est_Trip_time;
		this.seats_available = seats_available;
		this.compleated = compleated;
		this.vehicle = vehicle;
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getStartpoint() {
		return startpoint;
	}

	public void setStartpoint(String startpoint) {
		this.startpoint = startpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public int getEst_Trip_time() {
		return est_Trip_time;
	}

	public void setEst_Trip_time(int est_Trip_time) {
		this.est_Trip_time = est_Trip_time;
	}

	public int getSeats_available() {
		return seats_available;
	}

	public void setSeats_available(int seats_available) {
		this.seats_available = seats_available;
	}

	public boolean isCompleated() {
		return compleated;
	}

	public void setCompleated(boolean compleated) {
		this.compleated = compleated;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="trip")
	public Set<Registration> getRegistrations() {
		return this.registrations;
	}
	
	public void setRegistrations(Set<Registration> registrations) {
		this.registrations = registrations;
	}
	
	public void addRegistration(Registration registration) {
		this.registrations.add(registration);
		registration.setTrip(this);
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="trip")
	public Set<TripNode> getTripNodes() {
		return this.tripNodes;
	}
	
	public void setTripNodes(Set<TripNode> tripNodes) {
		this.tripNodes = tripNodes;
	}
	
	public void addTripNode(TripNode tripNode) {
		this.tripNodes.add(tripNode);
		tripNode.setTrip(this);
	}
	
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}