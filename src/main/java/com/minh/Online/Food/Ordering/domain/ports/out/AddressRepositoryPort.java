package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepositoryPort {
    Address save(Address address);
    Optional<Address> findByIdAndUserId(Long id, Long userId);
    List<Address> findByUserIdOrderByDefaultFirst(Long userId);
    void clearDefaultForUser(Long userId);
    void deleteById(Long id);
}
