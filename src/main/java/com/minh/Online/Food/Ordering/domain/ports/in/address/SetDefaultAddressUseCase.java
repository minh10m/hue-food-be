package com.minh.Online.Food.Ordering.domain.ports.in.address;

import com.minh.Online.Food.Ordering.domain.model.Address;

public interface SetDefaultAddressUseCase {
    Address setDefault(Long userId, Long addressId);
}
