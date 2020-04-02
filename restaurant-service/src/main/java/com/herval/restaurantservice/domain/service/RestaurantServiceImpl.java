package com.herval.restaurantservice.domain.service;

import com.herval.restaurantservice.common.DuplicateRestaurantException;
import com.herval.restaurantservice.domain.model.entity.Entity;
import com.herval.restaurantservice.domain.model.entity.Restaurant;
import com.herval.restaurantservice.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service("restaurantService")
public class RestaurantServiceImpl extends BaseService<Restaurant, String> implements RestaurantService {

    private RestaurantRepository<Restaurant, String> restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository<Restaurant, String> restaurantRepository) {
        super(restaurantRepository);
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void add(Restaurant restaurant) throws Exception {
        if (restaurantRepository.containsName(restaurant.getName())) {
            Object[] args = {restaurant.getName()};
            throw new DuplicateRestaurantException("duplicateRestaurant", args);
        }

        if (restaurant.getName() == null || "".equals(restaurant.getName())) {
            Object[] args = {restaurant.getName()};
            throw new DuplicateRestaurantException("invalidRestaurant", args);
        }
        super.add(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) throws Exception {
        restaurantRepository.update(restaurant);
    }

    @Override
    public void delete(String id) throws Exception {
        restaurantRepository.remove(id);
    }

    @Override
    public Entity findById(String id) throws Exception {
        return restaurantRepository.get(id);
    }

    @Override
    public Collection<Restaurant> findByName(String name) throws Exception {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Collection<Restaurant> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
