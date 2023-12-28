package com.eticaret.userservice.controller;

import com.eticaret.userservice.dto.JwtAuthenticationResponse;
import com.eticaret.userservice.dto.RefreshTokenRequest;
import com.eticaret.userservice.dto.SignInRequest;
import com.eticaret.userservice.dto.SignUpRequest;
import com.eticaret.userservice.model.User;
import com.eticaret.userservice.services.AuthenticationService;
import com.eticaret.userservice.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Api(tags = "Projects")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @ApiOperation(value = "signup Projects")
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @ApiOperation(value = "signin Project")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest ){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @ApiOperation(value = "refresh Project")
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest ){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }



}
