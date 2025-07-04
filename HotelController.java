package com.example.HotelRoomsEnd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @PostMapping("/addRoom")
    public Room addRoom(@RequestBody Room room) {
        return roomRepo.save(room);
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @PostMapping("/book")
    public String bookRoom(@RequestBody Booking booking) {
        Room room = roomRepo.findById(booking.getRoom().getId()).orElse(null);
        if (room != null && room.isAvail()) {
            room.setAvail(false);
            roomRepo.save(room);
            bookingRepo.save(booking);
            return "Room booked successfully!";
        } else {
            return "Room not available!";
        }
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
}
