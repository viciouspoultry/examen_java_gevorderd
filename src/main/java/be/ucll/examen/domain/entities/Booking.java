package be.ucll.examen.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="time_from")
    private LocalDateTime timeFrom;
    @Column(name ="time_to")
    private LocalDateTime timeTo;
    @Column(name = "user_comment")
    private String userComment;

    @Column(name ="booking_capacity")
    private Integer bookingCapacity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-booking")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "bookings_rooms",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> bookedRooms = new HashSet<>();


    // CONSTRUCTORS
    public Booking() {}


    // GETTERS AND SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimeFrom() { return timeFrom; }
    public void setTimeFrom(LocalDateTime timeFrom) { this.timeFrom = timeFrom; }

    public LocalDateTime getTimeTo() { return timeTo; }
    public void setTimeTo(LocalDateTime timeTo) { this.timeTo = timeTo; }

    public String getUserComment() { return userComment; }
    public void setUserComment(String userComment) { this.userComment = userComment; }

    public Integer getBookingCapacity() { return bookingCapacity; }
    public void setBookingCapacity(Integer bookingCapacity) { this.bookingCapacity = bookingCapacity; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Set<Room> getBookedRooms() { return bookedRooms; }
    public void setBookedRooms(Set<Room> bookedRooms) { this.bookedRooms = bookedRooms; }

    @PostLoad
    @PreUpdate
    private void updateBookingCapacity() {
        this.bookingCapacity = this.bookedRooms.stream()
            .mapToInt(room -> room.getCapacity())
            .sum();
    }
}
