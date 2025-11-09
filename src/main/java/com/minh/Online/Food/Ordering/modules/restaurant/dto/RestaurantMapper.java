package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public RestaurantDto toDto(Restaurant r) {
        if (r == null) return null;
        RestaurantDto dto = new RestaurantDto();
        dto.setId(r.getId());
        dto.setTitle(r.getName());
        dto.setDescription(r.getDescription());
        dto.setImage(r.getImage());
        dto.setOpen(r.isOpen());
        dto.setOpeningHours(r.getOpeningHours());
        if (r.getAddress() != null) {
            dto.setCity(r.getAddress().getCity());
            dto.setStreet(r.getAddress().getStreet());
        }
        return dto;
    }
}
