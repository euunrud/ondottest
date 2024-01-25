package com.ondot.ondot_back.global.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AuthDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long insertRefreshToken(Long organizationid, String refreshToken) {
        String insertRefreshTokenQuery = "insert into token(organization_id, refresh_token) values (?,?)";
        Object[] insertRefreshTokenParams = new Object[]{organizationid, refreshToken};

        this.jdbcTemplate.update(insertRefreshTokenQuery, insertRefreshTokenParams);

        return organizationid;
    }

    public String updateRefreshToken(Long organizationid, String newRefreshToken) {
        String updateRefreshTokenQuery = "update token set refresh_token = ? where organization_id=?";
        Object[] updateRefreshTokenParams = new Object[]{newRefreshToken, organizationid};

        this.jdbcTemplate.update(updateRefreshTokenQuery, updateRefreshTokenParams);

        return newRefreshToken;
    }

    public boolean checkRefreshToken(String token) {
        String checkRefreshTokenQuery = "select exists(select refresh_token from token where refresh_token = ?)";

        int result = this.jdbcTemplate.queryForObject(checkRefreshTokenQuery, boolean.class, token)? 1 : 0;

        if (result != 1)
            return false;

        return true;
    }

    public boolean checkOrganization(Long organizationid) {
        String checkUserQuery = "select exists(select organization_id from token where organization_id=?)";

        int result = this.jdbcTemplate.queryForObject(checkUserQuery, boolean.class, organizationid)? 1 : 0;

        if (result != 1)
            return false;

        return true;
    }

}
