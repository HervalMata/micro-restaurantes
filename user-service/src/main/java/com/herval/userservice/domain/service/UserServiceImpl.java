package com.herval.userservice.domain.service;

import com.herval.userservice.common.DuplicateUserException;
import com.herval.userservice.domain.model.entity.Entity;
import com.herval.userservice.domain.model.entity.User;
import com.herval.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseService<User, String> implements UserService {

    private UserRepository<User, String> userRepository;

    public UserServiceImpl(UserRepository<User, String> userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) throws Exception {
        if (userRepository.containsName(user.getName())) {
            Object[] args = {user.getName()};
            throw new DuplicateUserException("duplicateUser", args);
        }

        if (user.getName() == null || "".equals(user.getName())) {
            Object[] args = {user.getName()};
            throw new DuplicateUserException("invalidUser", args);
        }
        super.add(user);
    }

    @Override
    public void update(User user) throws Exception {
        userRepository.update(user);
    }

    @Override
    public void delete(String id) throws Exception {
        userRepository.remove(id);
    }

    @Override
    public Entity findById(String id) throws Exception {
        return userRepository.get(id);
    }

    @Override
    public Collection<User> findByName(String name) throws Exception {
        return userRepository.findByName(name);
    }

    @Override
    public Collection<User> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
