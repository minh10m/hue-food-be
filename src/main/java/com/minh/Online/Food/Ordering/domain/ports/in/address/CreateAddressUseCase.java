package com.minh.Online.Food.Ordering.domain.ports.in.address;

import com.minh.Online.Food.Ordering.domain.model.Address;

public interface CreateAddressUseCase {
    Address create(Address address);
}
