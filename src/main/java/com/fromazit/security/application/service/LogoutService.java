package com.fromazit.security.application.service;

import com.fromazit.security.application.usecase.LogoutUseCase;
import com.fromazit.security.info.CustomUserPrincipal;
import com.fromazit.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {
        UUID userId = principal.getId();

        refreshTokenRepository.deleteById(userId);
    }
}
