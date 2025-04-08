package com.oneultanda.userservice.presentaion.controller;

import com.oneultanda.userservice.application.service.UserService;
import com.oneultanda.userservice.presentaion.dto.request.RegisterUserRequest;
import com.oneultanda.userservice.presentaion.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;
    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterUserRequest request) {
        userservice.registerUser(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * todo: 없는 url로 요청이 올때 에러처리가 가능한가? 이부분은 gateway에서 처리할 것으로 예상
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("X-User-ID") Long userId) {
        UserResponse response = userservice.getUser(userId);
        return ResponseEntity.ok(response);
    }
//
//
//    @PutMapping
//
//    @DeleteMapping
}
