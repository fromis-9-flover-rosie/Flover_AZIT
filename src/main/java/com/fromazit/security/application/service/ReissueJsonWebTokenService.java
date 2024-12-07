package com.fromazit.security.application.service;

import com.fromazit.core.exception.error.ErrorCode;
import com.fromazit.core.exception.type.CommonException;
import com.fromazit.core.utility.JsonWebTokenUtil;
import com.fromazit.security.application.dto.response.DefaultJsonWebTokenDto;
import com.fromazit.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.fromazit.security.domain.mysql.User;
import com.fromazit.security.domain.redis.RefreshToken;
import com.fromazit.security.domain.service.RefreshTokenService;
import com.fromazit.security.repository.mysql.UserRepository;
import com.fromazit.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueJsonWebTokenService implements ReissueJsonWebTokenUseCase {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(String refreshTokenValue) {

        // refresh Token 검증. Redis에 있는 토큰인지 확인 -> userId 추출
        RefreshToken refreshToken = refreshTokenRepository.findByValue(refreshTokenValue)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
        UUID userId = refreshToken.getAccountId();

        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                user.getId(),
                user.getRole()
        );

        // Refresh Token 갱신
        refreshTokenRepository.save(refreshTokenService.createRefreshToken(user.getId(), defaultJsonWebTokenDto.getRefreshToken()));

        return defaultJsonWebTokenDto;
    }
}
