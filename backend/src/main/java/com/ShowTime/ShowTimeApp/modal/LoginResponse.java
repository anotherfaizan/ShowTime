package com.ShowTime.ShowTimeApp.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userName;
    private List<String> roles;
    private String jwtToken;
}
