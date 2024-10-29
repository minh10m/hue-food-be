package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.model.Address;
import com.minh.Online.Food.Ordering.model.Restaurant;
import com.minh.Online.Food.Ordering.model.User;
import com.minh.Online.Food.Ordering.repository.AddressRepository;
import com.minh.Online.Food.Ordering.repository.RestaurantRepository;
import com.minh.Online.Food.Ordering.repository.UserRepository;
import com.minh.Online.Food.Ordering.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

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
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
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
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
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
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFavorites = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorites = true;
                break;
            }
        }

        if (isFavorites) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }
        else {
            favorites.add(restaurantDto);
        }

        userRepository.save(user);
        return restaurantDto;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);


    }
}
