package be.ucll.examen.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="time_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFrom;
    @Column(name ="time_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTo;
    @Column(name = "user_comment")
    private String userComment;
    private Integer bookingCapacity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-booking")
    private UserEntity user;
    @ManyToMany
    @JoinTable(
            name = "bookings_rooms",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<RoomEntity> bookedRooms = new HashSet<>();


    // CONSTRUCTORS
    public BookingEntity() {}


    // GETTERS AND SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getTimeFrom() { return timeFrom; }
    public void setTimeFrom(Date timeFrom) { this.timeFrom = timeFrom; }

    public Date getTimeTo() { return timeTo; }
    public void setTimeTo(Date timeTo) { this.timeTo = timeTo; }

    public String getUserComment() { return userComment; }
    public void setUserComment(String userComment) { this.userComment = userComment; }

    public Integer getBookingCapacity() { return bookingCapacity; }
    public void setBookingCapacity(Integer bookingCapacity) { this.bookingCapacity = bookingCapacity; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public Set<RoomEntity> getBookedRooms() { return bookedRooms; }
    public void setBookedRooms(Set<RoomEntity> bookedRooms) { this.bookedRooms = bookedRooms; }

    @PostLoad
    @PreUpdate
    private void updateBookingCapacity() {
        this.bookingCapacity = this.bookedRooms.stream()
            .mapToInt(room -> room.getCapacity())
            .sum();
    }
}
