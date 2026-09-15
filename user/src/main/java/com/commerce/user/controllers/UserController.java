package com.commerce.user.controllers;

import com.commerce.user.dto.UserRequest;
import com.commerce.user.dto.UserResponse;
import com.commerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

   @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUser(),
                                    HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        return userService.fetchAUser(id).
                map(ResponseEntity::ok).
                orElseGet(()-> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest ){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added successfully");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id,
                                           @RequestBody  UserRequest updateUserRequest){
       boolean updated= userService.updateUser(id, updateUserRequest);
       if(updated)
           return ResponseEntity.ok("user edited successfully");
       return ResponseEntity.notFound().build();

    }


}
