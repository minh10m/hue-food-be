package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUserByJwtToken(String username);

    public User findUserByEmail(String email) throws Exception;
}
