package com.fromazit.security.application.controller;

import com.fromazit.core.annotation.security.UserID;
import com.fromazit.core.constant.Constants;
import com.fromazit.core.dto.ResponseDto;
import com.fromazit.core.exception.error.ErrorCode;
import com.fromazit.core.exception.type.CommonException;
import com.fromazit.core.utility.HeaderUtil;
import com.fromazit.security.application.dto.request.SignUpRequestDto;
import com.fromazit.security.application.dto.response.DefaultJsonWebTokenDto;
import com.fromazit.security.application.usecase.DeleteUserUseCase;
import com.fromazit.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.fromazit.security.application.usecase.SignUpUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Hidden
public class AuthController {
    private final SignUpUseCase signUpUseCase;
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /**
     * 1.3 JWT 재발급
     */
    @PostMapping("/api/v1/auth/reissue/token")
    public ResponseDto<DefaultJsonWebTokenDto> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(refreshToken));
    }

    /**
     * 2.1 일반 회원가입
     */
    @PostMapping("/api/v1/auth/sign-up")
    public ResponseDto<Void> signUp(
            @RequestBody @Valid SignUpRequestDto requestDto
            ) {
        signUpUseCase.execute(requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 2.2 회원 탈퇴
     */
    @DeleteMapping("/api/v1/auth")
    public ResponseDto<?> deleteAccount(
            @UserID UUID userId
    ) {
        deleteUserUseCase.execute(userId);
        return ResponseDto.ok(null);
    }
}
