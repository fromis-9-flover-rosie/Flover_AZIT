package com.fromazit.security.application.usecase;

import com.fromazit.core.annotation.bean.UseCase;
import com.fromazit.security.application.dto.request.SignUpRequestDto;

@UseCase
public interface SignUpUseCase {
    void execute(SignUpRequestDto requestDto);
}
