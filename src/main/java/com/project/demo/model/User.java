package com.project.demo.model;

// The User.java class directly represents app_user database rows

public class User {

    private Long id;
    private String username;
    private String passwordHash;
    private String email;

    public User() {}

    public User(Long id, String username, String passwordHash, String email) {

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
