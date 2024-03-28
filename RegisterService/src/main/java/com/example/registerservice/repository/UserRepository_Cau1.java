package com.example.registerservice.repository;

import com.example.registerservice.entity.User_Cau1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository_Cau1 extends JpaRepository<User_Cau1, Long> {
    User_Cau1 findByUsernameAndPassword(String username, String password);
}
