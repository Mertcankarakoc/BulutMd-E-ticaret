package com.eticaret.userservice.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}
