package com.project.demo.service;


import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public boolean login (String username, String password) {

        return repo.authenticateUser(username, password);

    }

    public Long register(User user) {

        return repo.registerUser(user);

    }

}
