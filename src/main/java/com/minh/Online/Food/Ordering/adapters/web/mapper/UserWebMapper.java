package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.UpdateUserRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.UserProfileDTO;
import com.minh.Online.Food.Ordering.domain.model.UserAccount;

public final class UserWebMapper {
    private UserWebMapper(){}

    public static UserProfileDTO toProfileDTO(UserAccount u){
        UserProfileDTO d = new UserProfileDTO();
        d.setId(u.id());
        d.setEmail(u.email());
        d.setFullName(u.fullName());
        d.setAvatarUrl(u.avatarUrl());
        d.setRole(u.role().name());
        d.setEnabled(u.enabled());
        return d;
    }

    public static String fullName(UpdateUserRequest r){ return r.getFullName(); }
    public static String avatar(UpdateUserRequest r){ return r.getAvatarUrl(); }
}

