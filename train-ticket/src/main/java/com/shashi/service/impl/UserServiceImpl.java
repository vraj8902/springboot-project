package com.shashi.service.impl;

import com.shashi.entity.User;
import com.shashi.repository.UserRepository;
import com.shashi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
return userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found: " + username));

    }
}
