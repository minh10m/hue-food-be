package com.minh.Online.Food.Ordering.domain.ports.in.address;

import com.minh.Online.Food.Ordering.domain.model.Address;

import java.util.List;

public interface ListAddressUseCase {
    List<Address> list(Long userId);
}
