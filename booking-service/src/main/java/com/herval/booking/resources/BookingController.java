package com.herval.booking.resources;

import com.herval.booking.common.DuplicateBookingException;
import com.herval.booking.common.InvalidBookingException;
import com.herval.booking.common.BookingNotFoundException;
import com.herval.booking.domain.model.entity.Entity;
import com.herval.booking.domain.model.entity.Booking;
import com.herval.booking.domain.service.BookingService;
import com.herval.booking.domain.valueobject.BookingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    protected static final Logger logger = Logger.getLogger(BookingController.class.getName());
    protected BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Booking>> findByName(@RequestParam("name") String name) throws Exception {
        logger.info(String.format("booking-service findByName() invoke:{} for {}", bookingService.getClass().getName()));
        name = name.trim().toLowerCase();
        Collection<Booking> bookings;
        try {
            bookings = bookingService.findByName(name);
        } catch (BookingNotFoundException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call", e);
            throw e;
        }
        return bookings.size() > 0 ? new ResponseEntity<>(bookings, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Entity> findById(@PathVariable("id") String id) throws Exception {
        logger.info(String.format("booking-service findById() invoke:{} for {}", bookingService.getClass().getName(), id));
        id = id.trim();
        Entity booking;
        try {
            booking = bookingService.findById(id);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised findById REST Call {0}", e);
            throw e;
        }
        return booking != null ? new ResponseEntity<>(booking, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Booking> add(@RequestBody BookingVO bookingVO) throws Exception {
        logger.info(String.format("booking-service add() invoke:%s for %s", bookingService.getClass().getName(), bookingVO.getName()));
        System.out.println(bookingVO);
        Booking booking = Booking.getDummyBooking();
        BeanUtils.copyProperties(bookingVO, booking);
        try {
            bookingService.add(booking);
        } catch (DuplicateBookingException | InvalidBookingException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call {0)", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call {};0", e);
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
