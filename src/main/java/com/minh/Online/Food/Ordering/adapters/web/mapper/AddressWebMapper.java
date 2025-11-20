package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.AddressRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.AddressResponse;
import com.minh.Online.Food.Ordering.domain.model.Address;

public final class AddressWebMapper {
    private AddressWebMapper() {}

    public static Address toDomain(Long userId, AddressRequest req) {
        return new Address(
                null,
                userId,
                req.getPhone(),
                req.getStreet(),
                req.getCity(),
                req.isDefault()
        );
    }

    public static AddressResponse toResponse(Address a) {
        AddressResponse r = new AddressResponse();
        r.setId(a.id());
        r.setUserId(a.userId());
        r.setPhone(a.phone());
        r.setStreet(a.street());
        r.setCity(a.city());
        r.setDefault(a.isDefault());
        return r;
    }
}

