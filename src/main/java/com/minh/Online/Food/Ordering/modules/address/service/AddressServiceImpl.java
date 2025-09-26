package com.minh.Online.Food.Ordering.modules.address.service;

import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.address.AddressRepository;
import com.minh.Online.Food.Ordering.modules.address.dto.AddressRequest;
import com.minh.Online.Food.Ordering.modules.address.dto.AddressResponse;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressResponse create(Long userId, AddressRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Address entity = new Address();
        entity.setUser(user);
        entity.setPhone(req.getPhone());
        entity.setStreet(req.getStreet());
        entity.setCity(req.getCity());
        entity.setDefault(req.isDefault());

        if (req.isDefault()) {
            addressRepository.clearDefaultForUser(userId);
            entity.setDefault(true);
        }

        Address saved = addressRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public List<AddressResponse> list(Long userId) {
        return addressRepository.findByUserIdOrderByIsDefaultDescIdAsc(userId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public AddressResponse get(Long userId, Long addressId) {
        Address a = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        return toResponse(a);
    }

    @Override
    @Transactional
    public AddressResponse update(Long userId, Long addressId, AddressRequest req) {
        Address a = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        a.setPhone(req.getPhone());
        a.setStreet(req.getStreet());
        a.setCity(req.getCity());

        if (req.isDefault()) {
            addressRepository.clearDefaultForUser(userId);
            a.setDefault(true);
        }

        Address saved = addressRepository.save(a);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long addressId) {
        Address a = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        addressRepository.delete(a);
    }

    @Override
    @Transactional
    public AddressResponse setDefault(Long userId, Long addressId) {
        Address a = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressRepository.clearDefaultForUser(userId);
        a.setDefault(true);
        return toResponse(addressRepository.save(a));
    }

    private AddressResponse toResponse(Address a) {
        return AddressResponse.builder()
                .id(a.getId())
                .userId(a.getUser() != null ? a.getUser().getId() : null)
                .phone(a.getPhone())
                .street(a.getStreet())
                .city(a.getCity())
                .isDefault(a.isDefault())
//                .createdAt(a.getCreatedAt())
//                .updatedAt(a.getUpdatedAt())
                .build();
    }
}
