package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.config.JwtProvider;
import com.minh.Online.Food.Ordering.model.User;
import com.minh.Online.Food.Ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String username) {
        String email = jwtProvider.getEmailFromToken(username);
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
