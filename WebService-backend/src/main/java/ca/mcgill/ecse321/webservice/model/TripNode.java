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

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")
public class TripNode{

	private Long id; 
	private Position position;
	private PointType pointType;
	private String name;
	private String time;
	private Trip trip;
	// maybe in the future have an x and y position to put on a map 
	
	public TripNode() {
		this.setPosition(new Position("ontario"));
	}
	
	public TripNode(String name, PointType pointType,String time) {
		super();
		this.name= name;
		//this.position = position;
		this.pointType = pointType;
		this.time = time;
				//new Time(time);
		//this.position= new Position("ontario");
		this.setPosition(new Position("ontario"));
		System.out.println(this.position.getId());
		//this.trip = trip;
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonBackReference(value="tnp")
	@ManyToOne(cascade=CascadeType.ALL, optional=false)
	public Position getPosition() {
		return this.position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	


	public void setPointType(PointType value) {
		this.pointType = value;
    }

	@Enumerated(EnumType.STRING)
	public PointType getPointType() {
		return this.pointType;
    }
   
   public void setTime(String value) {
	   this.time = value;
   }

   public String getTime() {
	   return this.time;
   }
   
   public void setName(String name) {
	   this.name = name;
   }

   public String getName() {
	   return this.name;
   }

   @JsonBackReference(value="tnt")
   @ManyToOne(cascade=CascadeType.ALL, optional=false)
   public Trip getTrip() {
	   return this.trip;
   }

   public void setTrip(Trip trip) {
	   //trip.addTripNode(this);
	   this.trip = trip;
   }

}
