package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.UserFeedback;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.AppUserRespository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class HelloController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Backend!";
    }

    @Autowired
    private AppUserRespository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/greet")
    public  String sayGreet(@RequestParam String name) {
        return "Hello " + name;
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody User user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        userRepository.save(entity);
        return "User saved to DB: " + entity.getName();
    }

    @PostMapping("/feedback")
    public  String createFeedback(@RequestBody UserFeedback feedback) {
        return  "Thanks for your feedback," + feedback.getUser() + "!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        List<UserEntity> entities = userRepository.findAll();
        List<User> users = new ArrayList<>();

        for (UserEntity e: entities) {
            users.add(new User(e.getName(), e.getEmail()));
        }
        return users;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserEntity> entity = userRepository.findById(id);

        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID:" + id);
        }
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<UserEntity> entity = userRepository.findById(id);

        if (entity.isPresent()) {
            UserEntity existingUser = entity.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            return ResponseEntity.ok("updated user" + existingUser.getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID:" + id);
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional <UserEntity> entity = userRepository.findById(id);
        if (entity.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("user deleted:" + entity.get().getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID:" + id);
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered: " + user.getUsername();
    }
}
