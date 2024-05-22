package be.ucll.examen.domain.dto;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BookingDto {

    // DATA FIELDS
    private Long id;

    @JsonProperty("time_from")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeFrom;

    @JsonProperty("time_to")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeTo;
    @JsonProperty("user_comment")
    private String userComment;
    @JsonProperty("booking_capacity")
    private Integer bookingCapacity;
    @JsonBackReference(value = "user-booking")
    private UserDto user;
    @JsonProperty("booked_rooms")
    private Set<RoomDto> bookedRooms = new HashSet<>();


    // CONSTRUCTORS
    public BookingDto() {}


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

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public Set<RoomDto> getBookedRooms() { return bookedRooms; }
    public void setBookedRooms(Set<RoomDto> bookedRooms) { this.bookedRooms = bookedRooms; }
}
