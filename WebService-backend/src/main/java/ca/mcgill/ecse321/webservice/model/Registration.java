package ca.mcgill.ecse321.webservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@Entity
public class Registration {
	
	private Long id; 
	private Role role;
	private User user;
	private Trip trip;
	
	public Registration() {
	}
	
	public Registration(Role role,
						User user,
						Trip trip) {
		setRole(role);
		setUser(user);
		setTrip(trip);
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setRole(Role value) {
		this.role = value;
	}

	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return this.role;
	}

	@ManyToOne(cascade=CascadeType.ALL, optional=false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(cascade=CascadeType.ALL, optional=false)
	public Trip getTrip() {
		return this.trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}
