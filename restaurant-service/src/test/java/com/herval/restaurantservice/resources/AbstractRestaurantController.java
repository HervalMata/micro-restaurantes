package com.herval.restaurantservice.resources;

import com.herval.restaurantservice.domain.model.entity.Entity;
import com.herval.restaurantservice.domain.model.entity.Restaurant;
import com.herval.restaurantservice.domain.valueobject.RestaurantVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.logging.Logger;

public abstract class AbstractRestaurantController {

    protected static final String RESTAURANT = "1";

    protected static final String RESTAURANT_NAME = "Le Maurice";

    protected static final String RESTAURANT_ADDRESS = "228 rue de Rivoli, 75001, Paris";

    @Autowired
    RestaurantController restaurantController;

    @Test
    public void validRestaurantById() throws Exception {
        Logger.getGlobal().info("Start validRestaurantById test");
        ResponseEntity<Entity> restaurant = restaurantController.findById(RESTAURANT);

        Assert.assertEquals(HttpStatus.OK, restaurant.getStatusCode());
        Assert.assertTrue(restaurant.hasBody());
        Assert.assertNotNull(restaurant.getBody());
        Assert.assertEquals(RESTAURANT, restaurant.getBody().getId());
        Assert.assertEquals(RESTAURANT_NAME, restaurant.getBody().getName());
        Logger.getGlobal().info("End validRestaurantById test");
    }

    @Test
    public void validRestaurantByName() throws Exception {
        Logger.getGlobal().info("Start validRestaurantByName test");
        ResponseEntity<Collection<Restaurant>> restaurants = restaurantController.findByName(RESTAURANT_NAME);

        Assert.assertEquals(HttpStatus.OK, restaurants.getStatusCode());
        Assert.assertTrue(restaurants.hasBody());
        Assert.assertNotNull(restaurants.getBody());
        Assert.assertFalse(restaurants.getBody().isEmpty());
        Restaurant restaurant = (Restaurant) restaurants.getBody().toArray()[0];
        Assert.assertEquals(RESTAURANT, restaurant.getId());
        Assert.assertEquals(RESTAURANT_NAME, restaurant.getName());
        Logger.getGlobal().info("End validRestaurantByName test");
    }

    @Test
    public void validAdd() throws Exception {
        Logger.getGlobal().info("Start validAdd test");
        RestaurantVO restaurant = new RestaurantVO();
        restaurant.setId("999");
        restaurant.setName("Test Restaurant");

        ResponseEntity<Restaurant> restaurants = restaurantController.add(restaurant);
        Assert.assertEquals(HttpStatus.CREATED, restaurants.getStatusCode());
        Logger.getGlobal().info("End ValidAdd test");
    }
}
