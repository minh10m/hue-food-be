package com.minh.Online.Food.Ordering.modules.user.service;

import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.dto.UpdateUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
    
    public User updateUser(Long userId, UpdateUserRequest updateRequest) throws Exception;
}
