package be.ucll.examen.domain.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class RoomEntity {
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
    private CampusEntity campus;
    @ManyToMany(mappedBy = "bookedRooms")
    private Set<BookingEntity> bookedBy = new HashSet<>();


    // CONSTRUCTORS
    public RoomEntity() {}


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

    public CampusEntity getCampus() { return campus; }
    public void setCampus(CampusEntity campus) { this.campus = campus; }

    public Set<BookingEntity> getBookedBy() { return bookedBy; }
    public void setBookedBy(Set<BookingEntity> bookedBy) { this.bookedBy = bookedBy; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(capacity, that.capacity) && Objects.equals(floorNumber, that.floorNumber) && Objects.equals(campus, that.campus) && Objects.equals(bookedBy, that.bookedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, capacity, floorNumber, campus, bookedBy);
    }
}
