package com.oneultanda.userservice.application.service;

import com.oneultanda.userservice.application.dto.comand.RegisterUserCommand;
import com.oneultanda.userservice.common.exception.CustomException;
import com.oneultanda.userservice.domain.entity.User;
import com.oneultanda.userservice.domain.repository.UserRepository;
import com.oneultanda.userservice.presentaion.dto.response.UserResponse;
import com.oneultanda.userservice.presentaion.exception.PresentaionErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRespository;


    @Transactional
    public void registerUser(RegisterUserCommand command) {
        User user = command.toUser();
        userRespository.save(user);
    }

    /**
     * todo: 추후 만일 cqrs를 제대로 적용시 조회 / 생성,수정,삭제 db를 분리하고 각 서비스를 handler로 분리할 수 있음
     */
    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User user = userRespository.findById(userId).orElseThrow(() ->
                new CustomException(PresentaionErrorCode.RESOURCE_NOT_FOUND));
        return UserResponse.fromUser(user);
    }
}
