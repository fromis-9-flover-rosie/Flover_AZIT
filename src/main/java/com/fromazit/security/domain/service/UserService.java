package com.fromazit.security.domain.service;

import com.fromazit.security.domain.mysql.User;
import com.fromazit.security.domain.type.ESecurityRole;
import com.fromazit.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public CustomUserPrincipal createCustomUserPrincipalByUser(User user) {
        return CustomUserPrincipal.create(user);
    }

    public User createUser(
            String serialId,
            String password,
            String name,
            ESecurityRole role
    ) {
        return User.builder()
                .serialId(serialId)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
