package com.herval.booking.domain.service;

import com.herval.booking.common.DuplicateBookingException;
import com.herval.booking.domain.model.entity.Entity;
import com.herval.booking.domain.model.entity.Booking;
import com.herval.booking.domain.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service("userService")
public class BookingServiceImpl extends BaseService<Booking, String> implements BookingService {

    private BookingRepository<Booking, String> bookingRepository;

    public BookingServiceImpl(BookingRepository<Booking, String> bookingRepository) {
        super(bookingRepository);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void add(Booking booking) throws Exception {
        if (bookingRepository.containsName(booking.getName())) {
            Object[] args = {booking.getName()};
            throw new DuplicateBookingException("duplicateBooking", args);
        }

        if (booking.getName() == null || "".equals(booking.getName())) {
            Object[] args = {"Booking with null oe empty name"};
            throw new DuplicateBookingException("invalidBooking", args);
        }
        super.add(booking);
    }

    @Override
    public void update(Booking booking) throws Exception {
        bookingRepository.update(booking);
    }

    @Override
    public void delete(String id) throws Exception {
        bookingRepository.remove(id);
    }

    @Override
    public Entity findById(String id) throws Exception {
        return bookingRepository.get(id);
    }

    @Override
    public Collection<Booking> findByName(String name) throws Exception {
        return bookingRepository.findByName(name);
    }

    @Override
    public Collection<Booking> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
