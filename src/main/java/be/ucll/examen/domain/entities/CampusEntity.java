package be.ucll.examen.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "campus")
public class CampusEntity {

    // DATA FIELDS
    @Id
    private String name;
    private String address;
    @Column(name = "parking_capacity")
    private Integer parkingCapacity;
    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RoomEntity> rooms = new ArrayList<>();
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;


    // CONSTRUCTORS
    public CampusEntity() {}


    // GETTERS AND SETTERS
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getParkingCapacity() { return parkingCapacity; }
    public void setParkingCapacity(Integer parkingCapacity) { this.parkingCapacity = parkingCapacity; }

    public List<RoomEntity> getRooms() { return rooms; }
    public void setRooms(List<RoomEntity> rooms) { this.rooms = rooms; }

    public Integer getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(Integer numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    @PrePersist
    @PreUpdate
    @PostLoad
    private void updateNumberOfRooms() {
        this.numberOfRooms = rooms.size();
    }
}
