package com.minh.Online.Food.Ordering.modules.user.service;

import com.minh.Online.Food.Ordering.modules.token.JwtService;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.dto.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        try {
            // Remove 'Bearer ' prefix if present
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }
            
            // Extract email from JWT token
            String email = jwtService.extractEmail(jwt);
            
            // Find user by email
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new Exception("User not found with email: " + email));
        } catch (Exception e) {
            throw new Exception("Error finding user by JWT token: " + e.getMessage(), e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
    }
    
    @Override
    public User updateUser(Long userId, UpdateUserRequest updateRequest) throws Exception {
        try {
            // Find the existing user
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("User not found with id: " + userId));
            
            // Update user fields if they are not null in the request
            if (updateRequest.getFullName() != null) {
                existingUser.setFullName(updateRequest.getFullName());
            }
            
            // Check if email is being updated and if it's already taken
            if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(existingUser.getEmail())) {
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    throw new Exception("Email is already in use");
                }
                existingUser.setEmail(updateRequest.getEmail());
            }
            
            // Save the updated user
            return userRepository.save(existingUser);
            
        } catch (Exception e) {
            throw new Exception("Error updating user: " + e.getMessage(), e);
        }
    }
}
