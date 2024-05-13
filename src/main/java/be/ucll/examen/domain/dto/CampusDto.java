package be.ucll.examen.domain.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CampusDto {

    // DATA FIELDS
    private String name;
    private String address;
    @JsonProperty("parking_capacity")
    private Integer parkingCapacity;
    @JsonManagedReference(value = "campus-room")
    private List<RoomDto> rooms = new ArrayList<>();
    @JsonProperty("number_of_rooms")
    private Integer numberOfRooms;


    // CONSTRUCTORS
    public CampusDto() {}


    // GETTERS AND SETTERS
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getParkingCapacity() { return parkingCapacity; }
    public void setParkingCapacity(Integer parkingCapacity) { this.parkingCapacity = parkingCapacity; }

    public List<RoomDto> getRooms() { return rooms; }
    public void setRooms(List<RoomDto> rooms) { this.rooms = rooms; }

    public Integer getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(Integer numberOfRooms) { this.numberOfRooms = numberOfRooms; }
}
