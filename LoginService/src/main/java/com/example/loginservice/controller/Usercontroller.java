package com.example.loginservice.controller;

import com.example.loginservice.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/login")
public class Usercontroller {
    private final String apiGetUser = "http://localhost:8080/api/v1/registry/users";
    private final String apiCheckLogin = "http://localhost:8080/api/v1/registry/users/checkLogin";

    private final String apiGetUserById = "http://localhost:8080/api/v1/registry/users/{id}";
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/users")
    public List<Map<String, Object>> getAllUser(){
        String url = apiGetUser;
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {}
        );
        return responseEntity.getBody();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        String url = apiGetUser;
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<User>() {}
        );
        return responseEntity.getBody();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") long id){
        String url = apiGetUserById.replace("{id}", String.valueOf(id));
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {}
        );
        return responseEntity.getBody();
    }


    // Call API của Server Registry để check username va password
    @GetMapping("/check-username-password")
    public User checkUsernameAndPassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                apiCheckLogin+"?username="+username+"&password="+password,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {}
        );
        return responseEntity.getBody();
    }


    @GetMapping("/login")
    public String Login(@RequestParam("username") String username, @RequestParam("password") String password) {
        checkUsernameAndPassword(username, password);
        if (checkUsernameAndPassword(username, password) != null) {
            return "Login Success";
        } else {
            return "Login Fail";
        }
    }
}
