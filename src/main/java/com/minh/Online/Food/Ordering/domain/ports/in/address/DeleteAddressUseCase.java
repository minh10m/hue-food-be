package com.minh.Online.Food.Ordering.domain.ports.in.address;

public interface DeleteAddressUseCase {
    void delete(Long userId, Long addressId);
}
