package com.minh.Online.Food.Ordering.modules.category;

import com.minh.Online.Food.Ordering.modules.category.dto.CreateCategoryRequest;
import com.minh.Online.Food.Ordering.modules.category.service.CategoryService;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

@Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserRepository userRepository;
    
    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        
        // Fallback to database lookup if principal is not User
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    }

    @PostMapping("/admin/category")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CreateCategoryRequest req
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = getAuthenticatedUser(authentication);

            Category createdCategory = categoryService.createCategory(req.getName(), req.getRestaurantId(), user);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("Restaurant was not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "You need to create a restaurant before creating categories"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating category: " + errorMessage));
        }
    }


    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @PathVariable Long id) throws Exception {

        List<Category> categories = categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
