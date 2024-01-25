package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.global.common.ApiResponseStatus;
import com.ondot.ondot_back.global.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.ondot.ondot_back.global.common.ApiResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
public class UserProvider {
    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public Organization retrieveByOrganizationId(String organizationId) {

        if (checkOrganizationId(organizationId) == 0) //아이디 없음
            throw new CustomException(HttpStatus.NOT_FOUND);

        try {
            return userDao.selectByOrganizationId(organizationId);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
    }

    public Organization retrieveById(Long organization_id) {
        if (checkId(organization_id) == 0)
            throw new CustomException(HttpStatus.NOT_FOUND);
        try {
            return userDao.selectById(organization_id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
    }

    public int checkOrganizationId(String organizationId) {
        try {
            return userDao.checkOrganizationId(organizationId);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
    }


    public int checkId(Long id) {
        try {
            return userDao.checkId(id);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
    }
}
