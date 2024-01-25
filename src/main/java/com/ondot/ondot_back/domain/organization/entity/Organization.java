package com.ondot.ondot_back.domain.organization.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.ondot.ondot_back.domain.organization.dto.OrganizationUpdateRequest;
import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NonNull
//    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$",
//            message = "단체 ID는 4~16자 사이의 영문과 숫자만 허용합니다.")
    @Column(name = "organization_id", unique = true)
    private String organizationId;

    @NonNull
//    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,12}$",
//            message = "단체 이름은 2~12자 사이의 영문, 한글, 숫자를 허용합니다.")
    @Column(name = "organization_name")
    private String name;

//    @NonNull
//    @Length(min = 8, max = 20, message = "비밀번호는 8~20자 사이만 허용합니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
//            message = "비밀번호는 영문, 숫자, 특수기호(_ @ ? !)로 구성되어야 합니다.")
    @Column(name = "organization_password")
    private String password;

//    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type")
    private OrganizationType type;

//    @NonNull
    @Column(name = "organization_prifle_url")
    private String profileUrl;

    @Column(name = "organization_image_url")
    private String imageUrl;

    @Length(max = 20, message = "대표자 연락처는 20자 이하로 구성되어야 합니다..")
    @Column(name = "organization_contact")
    private String contact;

    @Length(max = 140, message = "단체 설명은 140자 이하로 구성되어야 합니다.")
    @Column(name = "organization_description")
    private String description;


    // OAuth 로그인에 사용
//    @Column(name = "auth_role")
//    private String role; //ROLE_USER, ROLE_ADMIN

    @Column(name = "organization_provider")
    private String provider;

    @Column(name = "organization_provider_id")
    private String providerId;

    @Builder
    public Organization(long id, String organizationId, String name, String password, OrganizationType type, String profileUrl, String imageUrl, String contact, String description, String provider, String providerId) {
        this.id = id;
        this.organizationId = organizationId;
        this.name = name;
        this.password = password;
        this.type = type;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.description = description;
        this.provider = provider;
        this.providerId = providerId;
    }
    @Builder
    public Organization(String organizationId, String name, String password, OrganizationType type, String profileUrl, String imageUrl, String contact, String description, String provider, String providerId) {
        this.organizationId = organizationId;
        this.name = name;
        this.password = password;
        this.type = type;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.description = description;
        this.provider = provider;
        this.providerId = providerId;
    }
    public Organization(String organizationId, String name, String password, OrganizationType type, String profileUrl) {
        this.organizationId = organizationId;
        this.name = name;
        this.password = password;
        this.type = type;
        this.profileUrl = profileUrl;
    }

//    public Organization(String organizationId, String name, String password, OrganizationType type, String profileUrl, String none, String none1, String none2, String none3) {
//        this.organizationId = organizationId;
//        this.name = name;
//        this.password = password;
//        this.type = type;
//        this.profileUrl = profileUrl;
//    }


    public Organization update(OrganizationUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }

        if (request.type() != null) {
            this.type = OrganizationType.valueOf(request.type());
        }

        if (request.profileUrl() != null) {
            this.profileUrl = request.profileUrl();
        }

        if (request.imageUrl() != null) {
            this.imageUrl = request.imageUrl();
        }

        if (request.contact() != null) {
            this.contact = request.contact();
        }

        if (request.description() != null) {
            this.description = request.description();
        }

        return this;
    }
//    public void setOrganizationId(String organizationId) {
//        this.organizationId = organizationId;
//    }
}
