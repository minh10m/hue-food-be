package com.minh.Online.Food.Ordering.adapters.persistence.address;

import com.minh.Online.Food.Ordering.adapters.persistence.user.SpringDataUserRepository;
import com.minh.Online.Food.Ordering.adapters.persistence.user.UserJpaEntity;
import com.minh.Online.Food.Ordering.domain.model.Address;
import com.minh.Online.Food.Ordering.domain.ports.out.AddressRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class AddressRepositoryAdapter implements AddressRepositoryPort {

    private final SpringDataAddressRepository repo;
    private final SpringDataUserRepository users;

    public AddressRepositoryAdapter(SpringDataAddressRepository repo, SpringDataUserRepository users) {
        this.repo = repo; this.users = users;
    }

    @Override
    @Transactional
    public Address save(Address a) {
        AddressJpaEntity e = toEntity(a);
        if (a.userId() != null && (e.getUser() == null || e.getUser().getId() == null)) {
            e.setUser(UserJpaEntity.builder().id(a.userId()).build());
        }
        AddressJpaEntity saved = repo.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Address> findByIdAndUserId(Long id, Long userId) {
        return repo.findByIdAndUserId(id, userId).map(this::toDomain);
    }

    @Override
    public List<Address> findByUserIdOrderByDefaultFirst(Long userId) {
        return repo.findByUserIdOrderByIsDefaultDescIdAsc(userId).stream().map(this::toDomain).toList();
    }

    @Override
    @Transactional
    public void clearDefaultForUser(Long userId) { repo.clearDefaultForUser(userId); }

    @Override
    @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private Address toDomain(AddressJpaEntity e) {
        return new Address(
                e.getId(),
                e.getUser() != null ? e.getUser().getId() : null,
                e.getPhone(),
                e.getStreet(),
                e.getCity(),
                e.isDefault()
        );
    }

    private AddressJpaEntity toEntity(Address a) {
        return AddressJpaEntity.builder()
                .id(a.id())
                .user(a.userId() == null ? null : UserJpaEntity.builder().id(a.userId()).build())
                .phone(a.phone())
                .street(a.street())
                .city(a.city())
                .isDefault(a.isDefault())
                .build();
    }
}

