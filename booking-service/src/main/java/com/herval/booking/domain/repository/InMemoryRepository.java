package com.herval.booking.domain.repository;

import com.herval.booking.domain.model.entity.Booking;
import com.herval.booking.common.BookingNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Repository("bookingRepository")
public class InMemoryRepository implements BookingRepository<Booking, String> {

    private static final Map<String, Booking> entities;

    static {
        entities = new ConcurrentHashMap<String, Booking>(Map.ofEntries(
                new AbstractMap.SimpleEntry<>("1", new Booking("!", "Booking 1", "1", "1", "1", LocalDate.now(), LocalTime.now())),
                new AbstractMap.SimpleEntry<>("2", new Booking("2", "Booking 2", "2", "2", "2", LocalDate.now(), LocalTime.now()))
        ));
    }

    @Override
    public boolean containsName(String name) {
        try {
            return !this.findByName(name).isEmpty();
        } catch (BookingNotFoundException e) {
            return false;
        } catch (Exception ex) {

        }
        return false;
    }

    @Override
    public Collection<Booking> findByName(String name) throws Exception {
        int noOfChars = name.length();
        Collection<Booking> bookings = entities.entrySet()
                .stream()
                .filter(u -> u.getValue().getName().toLowerCase()
                       .contains(name.toLowerCase().subSequence(0, noOfChars)))
                .collect(toList())
                .stream()
                .map(k -> k.getValue())
                .collect(toList());
        if (bookings != null && bookings.isEmpty()) {
            Object[] args = {name};
            throw new BookingNotFoundException("bookingNotFound", args);
        }
        return bookings;
    }

    @Override
    public void add(Booking entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void remove(String id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    @Override
    public void update(Booking entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public boolean contains(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Booking get(String id) {
        return entities.get(id);
    }

    @Override
    public Collection<Booking> getAll() {
        return entities.values();
    }
}
