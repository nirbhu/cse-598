package com.concussion.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private UserRole role;
}
