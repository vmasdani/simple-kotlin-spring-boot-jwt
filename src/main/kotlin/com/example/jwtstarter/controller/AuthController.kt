package com.example.jwtstarter.controller

import com.example.jwtstarter.dummydata.dummyUsers
import com.example.jwtstarter.helpermodel.User
import com.example.jwtstarter.requestmodel.LoginRequestModel
import com.example.jwtstarter.requestmodel.RegisterRequestModel
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val env: Environment
) {
    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginRequestModel): String {
        // Login with searching for a user in dummyUser
        val foundUser = dummyUsers.find { user -> user.username == loginData.username }

        if (foundUser != null) {
            if (BCryptPasswordEncoder().matches(loginData.password, foundUser.password)) {
                val secretKeyString = env.getProperty("jwt.secret")
                val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString))
                // Finally, return jwt
                return Jwts.builder().setSubject(loginData.username).signWith(key).compact()
            } else return "Password incorrect!"
        }
        else return "User not found!"
    }

    @PostMapping("/register")
    fun register(@RequestBody registerData: RegisterRequestModel) {

    }
}