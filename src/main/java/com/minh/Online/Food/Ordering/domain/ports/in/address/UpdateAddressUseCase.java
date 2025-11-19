package com.minh.Online.Food.Ordering.domain.ports.in.address;

import com.minh.Online.Food.Ordering.domain.model.Address;

import java.util.Optional;

public interface UpdateAddressUseCase {
    Optional<Address> update(Long userId, Long addressId, Address patch);
}
