package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class UserDao {
    private final OrganizationJpaRepository organizationJpaRepository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(OrganizationJpaRepository organizationJpaRepository, DataSource dataSource) {
        this.organizationJpaRepository = organizationJpaRepository;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Organization insertUser(Organization organization) {

//        String insertUserQuery = "insert into organization (organization_id, organization_name, organization_password, organization_type, organization_profile_url, organization_image_url, organization_contact, organization_description, organization_provider, organization_provider_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        Object[] insertUserParams = new Object[]{organization.getOrganizationId(), organization.getName(), organization.getPassword(), organization.getType(), organization.getProfileUrl(),
//                organization.getImageUrl(), organization.getContact(), organization.getDescription(), organization.getProvider(), organization.getProviderId()};
//
//        this.jdbcTemplate.update(insertUserQuery, insertUserParams);
//
//        Long lastInsertId = this.jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        Organization savedOrganization = organizationJpaRepository.save(organization);
        // 저장된 엔터티에서 ID를 가져와 설정합니다.
        Long lastInsertId = savedOrganization.getId();
        organization.setId(lastInsertId);

        return organization;
    }

    public Organization selectByOrganizationId(String organization_id) {
        return organizationJpaRepository.findSingleByOrganizationId(organization_id);

//        String selectByEmailQuery = "select id, organization_id, organization_name, organization_password, organization_type, organization_profile_url, organization_image_url, organization_contact, organization_description, organization_provider, organization_provider_id from organization where organization_organizationId = ?";
//        Object[] selectByEmailParams = new Object[]{name};
//        try {
//            return this.jdbcTemplate.queryForObject(selectByEmailQuery,
//                    (rs, rowNum) -> new Organization(
//                            rs.getLong("id"),
//                            rs.getString("organization_id"),
//                            rs.getString("organization_name"),
//                            rs.getString("organization_password"),
//                            OrganizationType.valueOf(rs.getString("organization_type")),
//                            rs.getString("organization_profile_url"),
//                            rs.getString("organization_image_url"),
//                            rs.getString("organization_contact"),
//                            rs.getString("organization_description"),
//                            rs.getString("organization_provider"),
//                            rs.getString("organization_provider_id")),
//                    selectByEmailParams);
    }

    public Organization selectById(Long id) {
        return organizationJpaRepository.findById(id).orElse(null);
//        String selectByIdQuery = "select id, organization_id, organization_name, organization_password, organization_type, organization_profile_url, organization_image_url, organization_contact, organization_description, organization_provider, organization_provider_id from organization where id = ?";
//        return this.jdbcTemplate.queryForObject(selectByIdQuery,
//                (rs, rowNum) -> new Organization(
//                        rs.getLong("id"),
//                        rs.getString("organization_id"),
//                        rs.getString("organization_name"),
//                        rs.getString("organization_password"),
//                        OrganizationType.valueOf(rs.getString("organization_type")),
//                        rs.getString("organization_profile_url"),
//                        rs.getString("organization_image_url"),
//                        rs.getString("organization_contact"),
//                        rs.getString("organization_description"),
//                        rs.getString("auth_provider"),
//                        rs.getString("auth_provider_id")),
//                id);
    }

    //일반로그인: 아이디 검증
    public int checkOrganizationId(String organization_id) {
        String checkOrganizationIdQuery = "select exists(select organization_id from organization where organization_id = ?)";
        Object[] checkOrganizationIdParams = new Object[]{organization_id};
        return this.jdbcTemplate.queryForObject(checkOrganizationIdQuery, boolean.class, checkOrganizationIdParams)? 1 : 0;
    }

    public int checkId(Long id) {
        String checkIdQuery = "select exists(select organization_name from organization where id = ?";
        Long checkIdParam = id;
        return this.jdbcTemplate.queryForObject(checkIdQuery, boolean.class, checkIdParam)? 1 : 0;
    }
}
