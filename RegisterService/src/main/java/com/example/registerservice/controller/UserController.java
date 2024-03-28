package com.example.registerservice.controller;

import com.example.registerservice.entity.User_Cau1;
import com.example.registerservice.repository.UserRepository_Cau1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registry")
public class UserController {
    @Autowired
    private UserRepository_Cau1 userRepository;

    private Jedis jedis = new Jedis();
    private final ObjectMapper objectMapper = new ObjectMapper();


    // Câu 1
    @GetMapping("/users")
    public List<User_Cau1> getListUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User_Cau1 getUserById(@PathVariable long id)  {
        String key = String.valueOf(id);

        // Kiểm tra xem user có trong cache không
        if (jedis.exists(key)) {
            User_Cau1 userInCache = new User_Cau1();
            userInCache.setId(id);
            userInCache.setUsername(jedis.hget(key, "username"));
            userInCache.setUsername(jedis.hget(key, "password"));
            userInCache.setUsername(jedis.hget(key, "email"));
            System.out.println("fetching from cache >>>>>>>>>>" + id);
            return userInCache;
        } else {
            // Nếu user không có trong cache, lấy từ cơ sở dữ liệu và lưu vào cache
            System.out.println("fetching from database >>>>>>>>>>" + id);
            User_Cau1 user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User_id " + id + " not found"));

            // Lưu user name vào cache
            jedis.hset(key,"username", user.getUsername());
            jedis.hset(key,"password", user.getPassword());
            jedis.hset(key,"email", user.getEmail());
            System.out.println("saved in cache");
            return user;
        }
    }

    @PostMapping("/users")
    public User_Cau1 saveUser(@RequestBody User_Cau1 user) {
        return userRepository.save(user);
    }

    // Câu 2
    @GetMapping("/users/checkLogin")
    public User_Cau1 checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
