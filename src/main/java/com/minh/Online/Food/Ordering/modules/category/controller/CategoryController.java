package com.minh.Online.Food.Ordering.modules.category.controller;

import com.minh.Online.Food.Ordering.modules.category.service.CategoryService;
import com.minh.Online.Food.Ordering.modules.category.model.Category;
import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @AuthenticationPrincipal User user) throws Exception {
        
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) throws Exception {
            
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        List<Category> categories = categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
