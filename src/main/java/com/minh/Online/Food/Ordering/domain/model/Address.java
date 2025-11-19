package com.minh.Online.Food.Ordering.domain.model;

import java.util.Objects;

public final class Address {
    private final Long id;
    private final Long userId;
    private final String phone;
    private final String street;
    private final String city;
    private final boolean isDefault;

    public Address(Long id, Long userId, String phone, String street, String city, boolean isDefault) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.isDefault = isDefault;
    }

    public Long id() { return id; }
    public Long userId() { return userId; }
    public String phone() { return phone; }
    public String street() { return street; }
    public String city() { return city; }
    public boolean isDefault() { return isDefault; }

    public Address withId(Long newId) {
        return new Address(newId, userId, phone, street, city, isDefault);
    }

    public Address withDefault(boolean value) {
        return new Address(id, userId, phone, street, city, value);
    }

    @Override public boolean equals(Object o){ return (o instanceof Address a) && Objects.equals(id, a.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
