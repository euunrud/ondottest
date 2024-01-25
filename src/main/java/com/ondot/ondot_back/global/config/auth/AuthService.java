package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthDao authDao;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(AuthDao authDao, JwtTokenProvider jwtTokenProvider) {
        this.authDao = authDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long registerRefreshToken(Long organizationid, String refreshToken) {
        if(authDao.checkOrganization(organizationid))
            authDao.updateRefreshToken(organizationid, refreshToken);
        else
            authDao.insertRefreshToken(organizationid,refreshToken);

        return organizationid;
    }

//    public Long modifyRefreshToken(Long userid, String refreshToken) {
//        this.authDao.updateRefreshToken(userid,refreshToken);
//
//        return userid;
//    }
}
