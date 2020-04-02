package com.herval.restaurantservice.domain.repository;

import com.herval.restaurantservice.common.RestaurantNotFoundException;
import com.herval.restaurantservice.domain.model.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Map.ofEntries;
import static java.util.stream.Collectors.toList;

@Repository("restaurantRepository")
public class InMemoryRepository implements RestaurantRepository<Restaurant, String> {

    private static Map<String, Restaurant> entities;

    static {
        entities = new ConcurrentHashMap<String, Restaurant>(ofEntries(
                new AbstractMap.SimpleEntry<>("1", new Restaurant("Le Meurice", "1", "228 rue de Rivoli, 75001, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("2", new Restaurant("L'Ambroisie", "2", "9 place des Vosges, 75004, Paris",
                        Optional.empty())),
                new AbstractMap.SimpleEntry<>("3", new Restaurant("Arpège", "3", "84, rue de Varenne, 75007, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("4", new Restaurant("Alain Ducasse au Plaza Athénée", "4",
                        "25 avenue de Montaigne, 75008, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("5", new Restaurant("Pavillon LeDoyen", "5", "1, avenue Dutuit, 75008, Paris",
                        Optional.empty())),
                new AbstractMap.SimpleEntry<>("6", new Restaurant("Pierre Gagnaire", "6", "6, rue Balzac, 75008, Paris",
                        Optional.empty())),
                new AbstractMap.SimpleEntry<>("7", new Restaurant("L'Astrance", "7", "4, rue Beethoven, 75016, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("8", new Restaurant("Pré Catelan", "8", "Bois de Boulogne, 75016, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("9", new Restaurant("Guy Savoy", "9", "18 rue Troyon, 75017, Paris", Optional.empty())),
                new AbstractMap.SimpleEntry<>("10", new Restaurant("Le Bristol", "10",
                        "112, rue du Faubourg St Honoré, 8th arrondissement, Paris", Optional.empty())))
        );
    }

    @Override
    public boolean containsName(String name) {
        try {
            return !this.findByName(name).isEmpty();
        } catch (RestaurantNotFoundException e) {
            return false;
        } catch (Exception ex) {

        }
        return false;
    }

    @Override
    public Collection<Restaurant> findByName(String name) throws Exception {
        int noOfChars = name.length();
        Collection<Restaurant> restaurants = entities.entrySet()
                .stream()
                .filter(u -> u.getValue().getName().toLowerCase()
                       .contains(name.toLowerCase().subSequence(0, noOfChars)))
                .collect(toList())
                .stream()
                .map(k -> k.getValue())
                .collect(toList());
        if (restaurants != null && restaurants.isEmpty()) {
            Object[] args = {name};
            throw new RestaurantNotFoundException("restaurantNotFound", args);
        }
        return restaurants;
    }

    @Override
    public void add(Restaurant entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void remove(String id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    @Override
    public void update(Restaurant entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }

    @Override
    public boolean contains(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Restaurant get(String id) {
        return entities.get(id);
    }

    @Override
    public Collection<Restaurant> getAll() {
        return entities.values();
    }
}
