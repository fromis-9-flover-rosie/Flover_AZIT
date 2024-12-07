package com.fromazit.security.application.service;

import com.fromazit.core.exception.error.ErrorCode;
import com.fromazit.core.exception.type.CommonException;
import com.fromazit.security.application.dto.request.SignUpRequestDto;
import com.fromazit.security.application.usecase.SignUpUseCase;
import com.fromazit.security.domain.mysql.User;
import com.fromazit.security.domain.service.UserService;
import com.fromazit.security.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    @Override
    @Transactional
    public void execute(SignUpRequestDto requestDto) {

        // 이미 가입된 아이디인지 확인
        userRepository.findBySerialId(requestDto.serialId())
                .ifPresent(user -> {
                    throw new CommonException(ErrorCode.ALREADY_EXIST_ID);
                });

        // 유저 생성
        User user = userService.createUser(
                requestDto.serialId(),
                bCryptPasswordEncoder.encode(requestDto.password()),
                requestDto.name(),
                requestDto.role()
        );
        userRepository.save(user);

    }
}
