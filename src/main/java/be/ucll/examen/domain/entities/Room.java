package be.ucll.examen.domain.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Integer capacity;
    @Column(name = "floor_number")
    private Integer floorNumber;
    @ManyToOne
    @JoinColumn(name = "campus_name")
    @JsonBackReference
    private Campus campus;
    @ManyToMany(mappedBy = "bookedRooms")
    private Set<Booking> bookedBy = new HashSet<>();


    // CONSTRUCTORS
    public Room() {}


    // GETTERS AND SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getFloorNumber() { return floorNumber; }
    public void setFloorNumber(Integer floorNumber) { this.floorNumber = floorNumber; }

    public Campus getCampus() { return campus; }
    public void setCampus(Campus campus) { this.campus = campus; }

    public Set<Booking> getBookedBy() { return bookedBy; }
    public void setBookedBy(Set<Booking> bookedBy) { this.bookedBy = bookedBy; }
}
