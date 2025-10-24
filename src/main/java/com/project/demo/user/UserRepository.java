package com.project.demo.user;

// The UserRepository.java executes raw SQL via JdbcTemplate

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate; // jdbcTemplate is a helper class that runs SQL

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper = (rs, rowNum) -> { // RowMapper maps a ResultSet row --> User object
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setEmail(rs.getString("email"));
        user.setLastLoginDtm(rs.getObject("last_login_dtm", java.time.OffsetDateTime.class));
        return user;
    };

    // Create
    public void save(User user) { // Define SQL strings manually

        String sql = """
                INSERT INTO app_user (username, password_hash, email, last_login_dtm) 
                VALUES (? ,? ,? ,?)
                """;

        jdbcTemplate.update(sql, user.getUsername(), user.getPasswordHash(), user.getEmail(), user.getLastLoginDTM(ZoneOffset.UTC));

    }

    // READ
    public Optional<User> findByUsername(String username) { // Optional<User> handles “maybe found, maybe not” cases cleanly
        String sql = "SELECT * FROM app_user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, username);
        return users.stream().findFirst();
    }

    // UPDATE

    public int updateEmail(int id, String newEmail) {
        String sql = "UPDATE app_user SET email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newEmail, id);
    }

    public int updatePassword(int id, String newPasswordHash) {
        String sql = "UPDATE app_user SET password_hash = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newPasswordHash, id);
    }

    // DELETE
    public int deleteById(int id) {
        String sql = "DELETE FROM app_user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Get all users
    public List<User> findAll() {
        String sql = "SELECT * FROM app_user ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // Find by ID
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM app_user WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.stream().findFirst();
    }


}