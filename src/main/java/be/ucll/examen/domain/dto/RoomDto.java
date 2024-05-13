package be.ucll.examen.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RoomDto {

    // DATA FIELDS
    private Long id;
    private String name;
    private String type;
    private Integer capacity;
    @JsonProperty("floor_number")
    private Integer floorNumber;
    @JsonBackReference(value = "campus-room")
    private CampusDto campus;

    // Including this coding gives following error:
//    1) Converter org.modelmapper.internal.converter.MergingCollectionConverter@64151ee failed to convert org.hibernate.collection.internal.PersistentSet to java.util.Set.
//
//    1 error] with root cause
//
//    org.hibernate.LazyInitializationException: failed to lazily initialize a collection, could not initialize proxy - no Session

//    @JsonIgnore
//    private Set<BookingDto> bookedBy = new HashSet<>();


    // CONSTRUCTORS
    public RoomDto() {}


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

    public CampusDto getCampus() { return campus; }
    public void setCampus(CampusDto campus) { this.campus = campus; }

//    public Set<BookingDto> getBookedBy() { return bookedBy; }
//    public void setBookedBy(Set<BookingDto> bookedBy) { this.bookedBy = bookedBy; }
}
