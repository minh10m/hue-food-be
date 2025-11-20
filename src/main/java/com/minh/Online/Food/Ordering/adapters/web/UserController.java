package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.UpdateUserRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.UserProfileDTO;
import com.minh.Online.Food.Ordering.adapters.web.mapper.UserWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.user.GetUserProfileUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.user.UpdateUserProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final GetUserProfileUseCase getUC;
    private final UpdateUserProfileUseCase updateUC;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> get(@PathVariable Long id) {
        return getUC.getById(id)
                .map(u -> ResponseEntity.ok(UserWebMapper.toProfileDTO(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req) {
        return updateUC.updateProfile(id, UserWebMapper.fullName(req), UserWebMapper.avatar(req))
                .map(u -> ResponseEntity.ok(UserWebMapper.toProfileDTO(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

