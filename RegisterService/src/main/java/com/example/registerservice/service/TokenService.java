package com.example.registerservice.service;

import com.example.registerservice.entity.Token;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}
