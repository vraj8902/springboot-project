package com.shashi.service;

import com.shashi.entity.User;

public interface UserService {
    User getUserByUsername(String username); // ✅ added method
}
