package com.example.registerservice.service;

import com.example.registerservice.authen.UserPrincipal;
import com.example.registerservice.entity.User;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
