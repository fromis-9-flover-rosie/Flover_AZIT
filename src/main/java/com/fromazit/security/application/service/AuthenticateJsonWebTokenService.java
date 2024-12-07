package com.fromazit.security.application.service;

import com.fromazit.core.exception.error.ErrorCode;
import com.fromazit.core.exception.type.CommonException;
import com.fromazit.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.fromazit.security.domain.mysql.User;
import com.fromazit.security.domain.service.UserService;
import com.fromazit.security.info.CustomUserPrincipal;
import com.fromazit.security.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public CustomUserPrincipal execute(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return userService.createCustomUserPrincipalByUser(user);
    }
}
