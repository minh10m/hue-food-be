package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.AddressRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.AddressResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.AddressWebMapper;
import com.minh.Online.Food.Ordering.domain.model.Address;
import com.minh.Online.Food.Ordering.domain.ports.in.address.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
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

    @PostMapping
    public ResponseEntity<AddressResponse> create(@PathVariable Long userId,
                                                  @Valid @RequestBody AddressRequest req) {
        Address created = createUC.create(AddressWebMapper.toDomain(userId, req));
        AddressResponse resp = AddressWebMapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/users/" + userId + "/addresses/" + resp.getId()))
                .body(resp);
    }

    @GetMapping
    public List<AddressResponse> list(@PathVariable Long userId) {
        return listUC.list(userId).stream().map(AddressWebMapper::toResponse).toList();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> get(@PathVariable Long userId,
                                               @PathVariable Long addressId) {
        return getUC.get(userId, addressId)
                .map(a -> ResponseEntity.ok(AddressWebMapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> update(@PathVariable Long userId,
                                                  @PathVariable Long addressId,
                                                  @Valid @RequestBody AddressRequest req) {
        return updateUC.update(userId, addressId, AddressWebMapper.toDomain(userId, req))
                .map(a -> ResponseEntity.ok(AddressWebMapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long addressId) {
        deleteUC.delete(userId, addressId);
    }

    @PatchMapping("/{addressId}/default")
    public ResponseEntity<AddressResponse> setDefault(@PathVariable Long userId,
                                                      @PathVariable Long addressId) {
        Address saved = setDefaultUC.setDefault(userId, addressId);
        return ResponseEntity.ok(AddressWebMapper.toResponse(saved));
    }
}

