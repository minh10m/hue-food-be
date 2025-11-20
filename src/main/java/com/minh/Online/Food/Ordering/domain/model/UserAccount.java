package com.minh.Online.Food.Ordering.domain.model;

import java.util.Objects;

public final class UserAccount {
    private final Long id;
    private final String email;
    private final String passwordHash; // domain chỉ giữ hash
    private final String fullName;
    private final String avatarUrl;
    private final UserRole role;
    private final boolean enabled;

    public UserAccount(Long id, String email, String passwordHash, String fullName,
                       String avatarUrl, UserRole role, boolean enabled) {
        this.id = id; this.email = email; this.passwordHash = passwordHash;
        this.fullName = fullName; this.avatarUrl = avatarUrl; this.role = role; this.enabled = enabled;
    }

    public Long id(){ return id; }
    public String email(){ return email; }
    public String passwordHash(){ return passwordHash; }
    public String fullName(){ return fullName; }
    public String avatarUrl(){ return avatarUrl; }
    public UserRole role(){ return role; }
    public boolean enabled(){ return enabled; }

    public UserAccount withId(Long newId){ return new UserAccount(newId,email,passwordHash,fullName,avatarUrl,role,enabled); }
    public UserAccount withPasswordHash(String h){ return new UserAccount(id,email,h,fullName,avatarUrl,role,enabled); }
    public UserAccount withProfile(String name,String avatar){ return new UserAccount(id,email,passwordHash,name,avatar,role,enabled); }

    @Override public boolean equals(Object o){ return (o instanceof UserAccount u) && Objects.equals(id,u.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

