package com.zarzmaacademy.dao;

import com.zarzmaacademy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	boolean existsByUserName(String userName);
}
