package com.example.jwtstarter.controller

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generator")
class Generator {
    @GetMapping("/jwtsecret")
    fun jwtSecret(): String {
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        val keyString = Encoders.BASE64.encode(key.encoded)

        return keyString
    }

    @GetMapping("/bcryptpassword")
    fun bcryptPassword(@RequestParam password: String? = ""): String {
        if (password != null) {
            return BCryptPasswordEncoder().encode(password)
        } else return "Password parameter not provided (enter ?password=<yourpassword>)"
    }
}