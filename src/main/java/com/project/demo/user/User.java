package com.project.demo.user;

// The User.java represents a user record in Java
// Maybe turn refactor into a record?

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class User { // Field names match db columns in concept, however not exact casing

    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private OffsetDateTime lastLoginDtm;


    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public OffsetDateTime getLastLoginDTM(ZoneOffset utc) { return lastLoginDtm; }
    public void setLastLoginDtm(OffsetDateTime lastLoginDtm) { this.lastLoginDtm = lastLoginDtm; } // OffsetDateTime handles time zones better than LocalDateTIme

}
