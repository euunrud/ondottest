package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.global.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserProvider userProvider;
    private final UserDao userDao;

    @Autowired
    public UserService(UserProvider userProvider, UserDao userDao) {
        this.userProvider = userProvider;
        this.userDao = userDao;
    }

    public Organization createUser(Organization organization) {
        // 아이디 중복 체크
        if (userProvider.checkOrganizationId(organization.getOrganizationId()) == 1) {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
        try {
            // 사용자 생성
            return this.userDao.insertUser(organization);
        } catch (RuntimeException e) {
            log.error("사용자 생성에 실패했습니다.", e.getMessage());
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
    }
}
