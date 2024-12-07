package com.fromazit.security.application.usecase;


import com.fromazit.core.annotation.bean.UseCase;
import com.fromazit.security.application.dto.response.DefaultJsonWebTokenDto;
import com.fromazit.security.info.CustomUserPrincipal;

@UseCase
public interface LoginUseCase {

    /**
     * Security에서 사용되는 Login 유스케이스
     * @param principal UserPrincipal
     * @param jsonWebTokenDto DefaultJsonWebTokenDto
     */
    void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto);
}
