package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Address;
import com.minh.Online.Food.Ordering.domain.ports.in.address.*;
import com.minh.Online.Food.Ordering.domain.ports.out.AddressRepositoryPort;
import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AddressUseCaseService implements
        CreateAddressUseCase, GetAddressUseCase, ListAddressUseCase,
        UpdateAddressUseCase, DeleteAddressUseCase, SetDefaultAddressUseCase {

    private final AddressRepositoryPort repo;
    private final UserRepositoryPort users;

    public AddressUseCaseService(AddressRepositoryPort repo, UserRepositoryPort users) {
        this.repo = repo;
        this.users = users;
    }

    @Override
    @Transactional
    public Address create(Address address) {
        if (address.userId() == null) throw new IllegalArgumentException("userId required");
        if (!users.existsById(address.userId())) throw new NoSuchElementException("User not found");

        if (address.isDefault()) {
            repo.clearDefaultForUser(address.userId());
        }
        return repo.save(address.withId(null));
    }

    @Override
    public Optional<Address> get(Long userId, Long addressId) {
        return repo.findByIdAndUserId(addressId, userId);
    }

    @Override
    public List<Address> list(Long userId) {
        return repo.findByUserIdOrderByDefaultFirst(userId);
    }

    @Override
    @Transactional
    public Optional<Address> update(Long userId, Long addressId, Address patch) {
        return repo.findByIdAndUserId(addressId, userId).map(existing -> {
            Address merged = new Address(
                    existing.id(),
                    existing.userId(),
                    patch.phone() != null ? patch.phone() : existing.phone(),
                    patch.street() != null ? patch.street() : existing.street(),
                    patch.city() != null ? patch.city() : existing.city(),
                    patch.isDefault() || existing.isDefault()
            );
            if (patch.isDefault()) {
                repo.clearDefaultForUser(userId);
                merged = merged.withDefault(true);
            }
            return repo.save(merged);
        });
    }

    @Override
    @Transactional
    public void delete(Long userId, Long addressId) {
        // ensure it belongs to the user
        Address a = repo.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new NoSuchElementException("Address not found"));
        repo.deleteById(a.id());
    }

    @Override
    @Transactional
    public Address setDefault(Long userId, Long addressId) {
        Address a = repo.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new NoSuchElementException("Address not found"));
        repo.clearDefaultForUser(userId);
        return repo.save(a.withDefault(true));
    }
}

