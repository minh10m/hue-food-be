package com.minh.Online.Food.Ordering.modules.address.service;

import com.minh.Online.Food.Ordering.modules.address.dto.AddressRequest;
import com.minh.Online.Food.Ordering.modules.address.dto.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse create(Long userId, AddressRequest req);
    List<AddressResponse> list(Long userId);
    AddressResponse get(Long userId, Long addressId);
    AddressResponse update(Long userId, Long addressId, AddressRequest req);
    void delete(Long userId, Long addressId);
    AddressResponse setDefault(Long userId, Long addressId);
}

