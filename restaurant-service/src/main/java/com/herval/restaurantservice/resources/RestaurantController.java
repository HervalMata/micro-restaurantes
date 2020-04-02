package com.herval.restaurantservice.resources;

import com.herval.restaurantservice.common.DuplicateRestaurantException;
import com.herval.restaurantservice.common.InvalidRestaurantException;
import com.herval.restaurantservice.common.RestaurantNotFoundException;
import com.herval.restaurantservice.domain.model.entity.Entity;
import com.herval.restaurantservice.domain.model.entity.Restaurant;
import com.herval.restaurantservice.domain.service.RestaurantService;
import com.herval.restaurantservice.domain.valueobject.RestaurantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    protected static final Logger logger = Logger.getLogger(RestaurantController.class.getName());
    protected RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Restaurant>> findByName(@RequestParam("name") String name) throws Exception {
        logger.info(String.format("restaurant-service findByName() invoke:{} for {}", restaurantService.getClass().getName()));
        name = name.trim().toLowerCase();
        Collection<Restaurant> restaurants;
        try {
            restaurants = restaurantService.findByName(name);
        } catch (RestaurantNotFoundException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call", e);
            throw e;
        }
        return restaurants.size() > 0 ? new ResponseEntity<>(restaurants, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Entity> findById(@PathVariable("id") String id) throws Exception {
        logger.info(String.format("restaurant-service findById() invoke:{} for {}", restaurantService.getClass().getName(), id));
        id = id.trim();
        Entity restaurant;
        try {
            restaurant = restaurantService.findById(id);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised findById REST Call {0}", e);
            throw e;
        }
        return restaurant != null ? new ResponseEntity<>(restaurant, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Restaurant> add(@RequestBody RestaurantVO restaurantVO) throws Exception {
        logger.info(String.format("restaurant-service add() invoke:%s for %s", restaurantService.getClass().getName(), restaurantVO.getName()));
        System.out.println(restaurantVO);
        Restaurant restaurant = Restaurant.getDummyRestaurant();
        BeanUtils.copyProperties(restaurantVO, restaurant);
        try {
            restaurantService.add(restaurant);
        } catch (DuplicateRestaurantException | InvalidRestaurantException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call {0)", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call {};0", e);
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
