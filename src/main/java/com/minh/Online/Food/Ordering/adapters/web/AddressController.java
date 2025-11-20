package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.security.AuthUtils;
import com.minh.Online.Food.Ordering.adapters.web.dto.AddressRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.AddressResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.AddressWebMapper;
import com.minh.Online.Food.Ordering.domain.model.Address;
import com.minh.Online.Food.Ordering.domain.ports.in.address.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    private final CreateAddressUseCase createUC;
    private final GetAddressUseCase getUC;
    private final ListAddressUseCase listUC;
    private final UpdateAddressUseCase updateUC;
    private final DeleteAddressUseCase deleteUC;
    private final SetDefaultAddressUseCase setDefaultUC;

    private final AuthUtils auth;

    @PostMapping
    public ResponseEntity<AddressResponse> create(
            Authentication authentication,
            @Valid @RequestBody AddressRequest req) {
        Long userId = auth.currentUserId(authentication);
        Address created = createUC.create(AddressWebMapper.toDomain(userId, req));
        AddressResponse resp = AddressWebMapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/users/" + userId + "/addresses/" + resp.getId()))
                .body(resp);
    }

    @GetMapping
    public List<AddressResponse> list(Authentication authentication) {
        Long userId = auth.currentUserId(authentication);
        return listUC.list(userId).stream().map(AddressWebMapper::toResponse).toList();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> get(
            Authentication authentication,
            @PathVariable Long addressId) {
        Long userId = auth.currentUserId(authentication);
        return getUC.get(userId, addressId)
                .map(a -> ResponseEntity.ok(AddressWebMapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> update(
            Authentication authentication,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest req) {
        Long userId = auth.currentUserId(authentication);
        return updateUC.update(userId, addressId, AddressWebMapper.toDomain(userId, req))
                .map(a -> ResponseEntity.ok(AddressWebMapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long addressId) {
        Long userId = auth.currentUserId(authentication);
        deleteUC.delete(userId, addressId);
    }

    @PatchMapping("/{addressId}/default")
    public ResponseEntity<AddressResponse> setDefault(
            Authentication authentication,
            @PathVariable Long addressId) {
        Long userId = auth.currentUserId(authentication);
        Address saved = setDefaultUC.setDefault(userId, addressId);
        return ResponseEntity.ok(AddressWebMapper.toResponse(saved));
    }
}

