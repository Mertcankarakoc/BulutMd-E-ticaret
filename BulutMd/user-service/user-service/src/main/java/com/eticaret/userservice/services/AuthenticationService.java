package com.eticaret.userservice.services;

import com.eticaret.userservice.dto.JwtAuthenticationResponse;
import com.eticaret.userservice.dto.RefreshTokenRequest;
import com.eticaret.userservice.dto.SignInRequest;
import com.eticaret.userservice.dto.SignUpRequest;
import com.eticaret.userservice.model.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
