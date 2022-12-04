package com.api.bonobo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.bonobo.model.Role;
import com.api.bonobo.model.User;
import com.api.bonobo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @GetMapping("/{username}")
    ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUser(username));
    }

    @PostMapping
    ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.created(URI.create("/users/" + user.getUsername())).body(userService.saveUser(user));
    }

    @PostMapping("/roles")
    ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.created(URI.create("/users/roles/" + role.getName())).body(userService.saveRole(role));
    }
}
