package com.project.demo.user;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

// The UserService.java will manipulate User objects without touching SQL directly
// Business Logic, password hashing, validation

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    public void registerUser(String username, String passwordHash, String email) {

        String hashedPassword = BCrypt.hashpw(passwordHash, BCrypt.gensalt()); // On registration, hash before saving
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setEmail(email);
        userRepository.save(user);

    }

    public Optional<User> login(String username, String passwordHash) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {

            User user = userOpt.get();
            if (BCrypt.checkpw(passwordHash, user.getPasswordHash())) { // on login, we compare hash with plaintext

                user.setLastLoginDtm(OffsetDateTime.now());
                userRepository.updateEmail(user.getId(), user.getEmail());
                return Optional.of(user);

            }


        }

        return Optional.empty();

    }

    public boolean updateEmail(int userId, String newEmail) {
        return userRepository.updateEmail(userId, newEmail) > 0;
    }

    public boolean updatePassword(int userId, String oldPassword, String newPassword) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {

            return false;

        }

        User user = userOpt.get();

        if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {

            return false;

        }

        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return userRepository.updatePassword(userId, newHash) > 0;

    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

}
