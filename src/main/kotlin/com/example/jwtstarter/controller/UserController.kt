package com.example.jwtstarter.controller

import com.example.jwtstarter.dummydata.dummyUsers
import com.example.jwtstarter.helpermodel.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    @GetMapping("/users")
    fun findAllUsers(): List<User> {
        return dummyUsers
    }
}