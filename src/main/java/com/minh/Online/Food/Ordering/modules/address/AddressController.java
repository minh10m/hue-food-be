package com.minh.Online.Food.Ordering.modules.address;

import com.minh.Online.Food.Ordering.modules.address.dto.AddressRequest;
import com.minh.Online.Food.Ordering.modules.address.dto.AddressResponse;
import com.minh.Online.Food.Ordering.modules.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse create(@PathVariable Long userId,
                                  @Valid @RequestBody AddressRequest req) {
        return addressService.create(userId, req);
    }

    @GetMapping
    public List<AddressResponse> list(@PathVariable Long userId) {
        return addressService.list(userId);
    }

    @GetMapping("/{addressId}")
    public AddressResponse get(@PathVariable Long userId,
                               @PathVariable Long addressId) {
        return addressService.get(userId, addressId);
    }

    @PutMapping("/{addressId}")
    public AddressResponse update(@PathVariable Long userId,
                                  @PathVariable Long addressId,
                                  @Valid @RequestBody AddressRequest req) {
        return addressService.update(userId, addressId, req);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId,
                       @PathVariable Long addressId) {
        addressService.delete(userId, addressId);
    }

    @PostMapping("/{addressId}/default")
    public AddressResponse setDefault(@PathVariable Long userId,
                                      @PathVariable Long addressId) {
        return addressService.setDefault(userId, addressId);
    }
}
