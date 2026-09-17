// package com.example.lets_play.controller;

// import com.example.lets_play.dto.UserRequest;
// import com.example.lets_play.model.User;
// import com.example.lets_play.service.UserService;

// import java.util.List;

// import jakarta.validation.Valid;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("api/User")
// public class UserController {
//     private final UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping
//     @ResponseStatus(HttpStatus.CREATED)
//     public User createUser(@RequestBody UserRequest User) {
//         return userService.createUser(User);
//     }

//     @GetMapping
//     @ResponseStatus(HttpStatus.OK)
//     public List<User> getAllUsers() {
//         return userService.getAllUsers();
//     }

//     @GetMapping("/{id}")
//     @ResponseStatus(HttpStatus.OK)
//     public User getUserById(@PathVariable String id) {
//         return userService.getUserById(id);
//     }

//     @PutMapping("/{id}")
//     @ResponseStatus(HttpStatus.OK)
//     public User updateUser(@PathVariable String id, @RequestBody User User) {
//         return userService.updateUser(id, User);
//     }

//     @DeleteMapping("/{id}")
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     public void deleteUser(@PathVariable String id) {
//         userService.deleteUser(id);
//     }
// }