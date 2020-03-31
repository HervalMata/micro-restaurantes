package com.herval.userservice.domain.repository;

import com.herval.userservice.common.UserNotFoundException;
import com.herval.userservice.domain.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Repository("userRepository")
public class InMemoryRepository implements  UserRepository<User, String>  {

    private static final Map<String, User> entities;

    static {
        entities = new ConcurrentHashMap<String, User>(Map.ofEntries(
                new AbstractMap.SimpleEntry<>("1", new User("!", "User Name 1", "Address 1", "City 1", "9999911111")),
                new AbstractMap.SimpleEntry<>("2", new User("2", "User Name 2", "Address 2", "City 2", "9999922222"))
        ));
    }

    @Override
    public boolean containsName(String name) {
        try {
            return !this.findByName(name).isEmpty();
        } catch (UserNotFoundException e) {
            return false;
        } catch (Exception ex) {

        }
        return false;
    }

    @Override
    public Collection<User> findByName(String name) throws Exception {
        int noOfChars = name.length();
        Collection<User> users = entities.entrySet()
                .stream()
                .filter(u -> u.getValue().getName().toLowerCase()
                       .contains(name.toLowerCase().subSequence(0, noOfChars)))
                .collect(toList())
                .stream()
                .map(k -> k.getValue())
                .collect(toList());
        if (users != null && users.isEmpty()) {
            Object[] args = {name};
            throw new UserNotFoundException("userNotFound", args);
        }
        return users;
    }

    @Override
    public void add(User entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void remove(String id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    @Override
    public void update(User entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public boolean contains(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User get(String id) {
        return entities.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return entities.values();
    }
}
