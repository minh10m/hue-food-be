package com.minh.Online.Food.Ordering.modules.restaurant.service;

import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantResponse;
import com.minh.Online.Food.Ordering.modules.restaurant.RestaurantRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.address.AddressRepository;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setAddress(address);
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImage(req.getImage());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDate.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);

    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateReq) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateReq.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(updateReq.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(updateReq.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurantsDto() {
        List<Restaurant> list = restaurantRepository.findAllWithOwnerAndAddress();
        return list.stream().map(r -> RestaurantResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .description(r.getDescription())
                .cuisineType(r.getCuisineType())
                .openingHours(r.getOpeningHours())
                .image(r.getImage())
                .open(r.isOpen())
                .ownerId(r.getOwner() != null ? r.getOwner().getId() : null)
                .ownerName(r.getOwner() != null ? r.getOwner().getFullName() : null)
                .addressId(r.getAddress() != null ? r.getAddress().getId() : null)
                .street(r.getAddress() != null ? r.getAddress().getStreet() : null)
                .city(r.getAddress() != null ? r.getAddress().getCity() : null)
                .build()
        ).toList();
    }


    @Override
    public List<Restaurant> searchRestaurant(String keyWord) {
        return restaurantRepository.findBySearchQuery(keyWord);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if (opt.isEmpty()){
            throw new Exception("Restaurant not found");
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null){
            throw new Exception("Restaurant was not found with ownerId: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, Long userId) {
        User user = userRepository.findByIdWithFavorites(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // toggle
        if (user.getFavorites().contains(restaurant)) {
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurant);
        }

        // save not strictly necessary if within persistence context, but ok:
        userRepository.save(user);

        // map to DTO to return
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setTitle(restaurant.getName());
        dto.setImage(restaurant.getImage());
        dto.setDescription(restaurant.getDescription());
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);


    }
}
