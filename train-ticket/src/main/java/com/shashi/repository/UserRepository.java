package com.shashi.repository;

import com.shashi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // ✅ This import is required

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
