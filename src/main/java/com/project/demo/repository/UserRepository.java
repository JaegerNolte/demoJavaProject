package com.project.demo.repository;

import com.project.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepository {

    @Autowired
    private DataSource dataSource;


    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // In real apps, use BCrypt hash

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long registerUser(User user) {

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            int affected = stmt.executeUpdate();

            if (affected > 0) {

                try (ResultSet rs = stmt.getGeneratedKeys()) {

                    if (rs.next()) return rs.getLong(1);

                }

            }

            return (long) -1;

        } catch (SQLException e) {

            e.printStackTrace();
            return (long) -1;
        }

    }

}

