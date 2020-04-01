package com.herval.userservice.resources;

import com.herval.userservice.common.DuplicateUserException;
import com.herval.userservice.common.InvalidUserException;
import com.herval.userservice.common.UserNotFoundException;
import com.herval.userservice.domain.model.entity.Entity;
import com.herval.userservice.domain.model.entity.User;
import com.herval.userservice.domain.service.UserService;
import com.herval.userservice.domain.valueobject.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    protected static final Logger logger = Logger.getLogger(UserController.class.getName());
    protected UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findByName(@RequestParam("name") String name) throws Exception {
        logger.info(String.format("user-service findByName() invoke:{} for {}", userService.getClass().getName()));
        name = name.trim().toLowerCase();
        Collection<User> users;
        try {
            users = userService.findByName(name);
        } catch (UserNotFoundException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call", e);
            throw e;
        }
        return users.size() > 0 ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Entity> findById(@PathVariable("id") String id) throws Exception {
        logger.info(String.format("user-service findById() invoke:{} for {}", userService.getClass().getName(), id));
        id = id.trim();
        Entity user;
        try {
            user = userService.findById(id);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception raised findById REST Call {0}", e);
            throw e;
        }
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody UserVO userVO) throws Exception {
        logger.info(String.format("user-service add() invoke:%s for %s", userService.getClass().getName(), userVO.getName()));
        System.out.println(userVO);
        User user = User.getDummyUser();
        BeanUtils.copyProperties(userVO, user);
        try {
            userService.add(user);
        } catch (DuplicateUserException | InvalidUserException e) {
            logger.log(Level.WARNING, "Exception raised findByName REST Call {0)", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception raised findByName REST Call {};0", e);
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
