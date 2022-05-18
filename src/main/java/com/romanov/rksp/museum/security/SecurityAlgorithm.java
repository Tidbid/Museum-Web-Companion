package com.romanov.rksp.museum.security;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityAlgorithm {
    private static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    public static Algorithm getAlgorithm() {
        return algorithm;
    }
}
