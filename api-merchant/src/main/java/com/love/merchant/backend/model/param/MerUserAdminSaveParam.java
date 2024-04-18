package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Data
@Schema(title = "MerUserAdminSaveParam")
public class MerUserAdminSaveParam implements Serializable {

    @Schema(description = "id", requiredMode = AUTO)
    private Long id;

    @Schema(description = "legal business name", requiredMode = REQUIRED, example = "love")
    @NotBlank(message = "bizName must not be blank")
    private String bizName;

    @Schema(description = "account", requiredMode = REQUIRED, example = "xxx@gmail.com")
    @NotBlank(message = "account must not be blank")
    @Email(message = "email format error")
    private String account;

    @Schema(description = "password", requiredMode = AUTO, example = "12345AwsERT")
    private String password;

    @Schema(description = "invite code", requiredMode = REQUIRED, example = "23erT8")
    @NotBlank(message = "code must not be blank")
    private String code;

    @Valid
    @NotNull(message = "personalInfo must not be null")
    @Schema(description = "admin personal info", requiredMode = REQUIRED)
    private PersonalInfo personalInfo;

    @Valid
    @NotNull(message = "businessInfo must not be null")
    @Schema(description = "admin business info", requiredMode = REQUIRED)
    private BusinessInfo businessInfo;

    @Valid
    @NotNull(message = "brands must not be null")
    @Schema(description = "brand list, less then 5 item", requiredMode = REQUIRED)
    private List<Brand> brands;

    @Data
    @Schema(title = "PersonalInfo")
    public static class PersonalInfo implements Serializable {

        @Schema(description = "adminId", requiredMode = AUTO, example = "1")
        private Long adminId;

        @Schema(description = "first name", requiredMode = REQUIRED, example = "evan")
        @NotBlank(message = "firstName must not be blank")
        private String firstName;

        @Schema(description = "last name", requiredMode = REQUIRED, example = "chen")
        @NotBlank(message = "lastName must not be blank")
        private String lastName;

        @Schema(description = "title", requiredMode = REQUIRED, example = "SEO")
        @NotBlank(message = "title must not be blank")
        private String title;

        @Schema(description = "birthday", requiredMode = REQUIRED, example = "2000-03-09")
        @Past(message = "birthday should be a past time")
        @NotNull(message = "birthday must not be null")
        private Date birthday;

        @Schema(description = "admin phone number", requiredMode = REQUIRED, example = "87962345")
        @NotBlank(message = "phoneNumber must not be blank")
        private String phoneNumber;
    }

    @Data
    @Schema(title = "BusinessInfo")
    public static class BusinessInfo implements Serializable {

        @Schema(description = "adminId", requiredMode = AUTO, example = "1")
        private Long adminId;

        @Schema(description = "legal business name", requiredMode = REQUIRED, example = "love")
        @NotBlank(message = "biz name must not be blank")
        private String bizName;

        @Schema(description = "business type >  1:Corporation (Private) " +
                "2:Corporation (Public) " +
                "3:International Organization " +
                "4:Limited Liability Company " +
                "5:Individual Sole Proprietorship " +
                "6:Partnership", requiredMode = REQUIRED, example = "1")
        @NotNull(message = "biz type must not be blank")
        @Range(min = 1, max = 6, message = "biz type must between 1 and 6")
        private Integer bizType;

        @Schema(description = "ownership > 1:Private 2:Public", requiredMode = REQUIRED, example = "1")
        @NotNull(message = "ownership must not be blank")
        @Range(min = 1, max = 2, message = "ownership must between 1 and 2")
        private Integer ownership;

        @Schema(description = "incorporation date", requiredMode = REQUIRED, example = "2020-07-09")
        @Past(message = "incorporation date should be a past time")
        @NotNull(message = "incorporation date must not be null")
        private Date incorDate;

        @Schema(description = "website", requiredMode = REQUIRED, example = "https://google.com")
        @NotBlank(message = "website must not be blank")
        @URL(message = "website should be a legal url")
        private String website;

        @Schema(description = "business phone number", requiredMode = REQUIRED, example = "12345678")
        @NotBlank(message = "business phone number must not be blank")
        private String bizPhoneNumber;

        @Schema(description = "country", requiredMode = REQUIRED, example = "US")
        @NotBlank(message = "country must not be blank")
        private String country;

        @Schema(description = "state", requiredMode = REQUIRED, example = "state")
        @NotNull(message = "state must not be blank")
        private String state;

        @Schema(description = "city", requiredMode = REQUIRED, example = "San Francisco")
        @NotBlank(message = "city must not be blank")
        private String city;

        @Schema(description = "post code", requiredMode = REQUIRED, example = "999039")
        @NotBlank(message = "zipCode must not be blank")
        private String zipCode;

        @Schema(description = "address", requiredMode = REQUIRED, example = "San Francisco xxx street")
        @NotBlank(message = "address must not be blank")
        private String address;

        @Schema(description = "bizOrderMgmtEmail", requiredMode = REQUIRED, example = "xxx@gmail.com")
        @NotBlank(message = "business order management email must not be blank")
        @Email(message = "email format error")
        private String bizOrderMgmtEmail;

        @Schema(description = "defaultCarrier", requiredMode = NOT_REQUIRED, example = "ups")
        private String defaultCarrier;

        @Schema(description = "business type >  " +
                "1:tiny business(under 25 employees)" +
                "2:small business(under 150 employees)" +
                "3:mid size business(under 500 employees)" +
                "4:mid-large business(under 1000 employees)" +
                "5:large business(over 1000 employees)", requiredMode = REQUIRED, example = "1")
        @NotNull(message = "bizSize type must not be null")
        @Range(min = 1, max = 6, message = "bizSize type must between 1 and 6")
        private Integer bizSize;
    }

    @Data
    @Schema(title = "Brand")
    public static class Brand implements Serializable {

        @Schema(description = "id", requiredMode = AUTO, example = "1")
        private Long id;

        @Schema(description = "brand name", requiredMode = REQUIRED, example = "adidas")
        @NotBlank(message = "bran name must not be blank")
        private String name;

        @Schema(description = "brand logo", requiredMode = REQUIRED)
        @NotBlank(message = "brand logo must not be blank")
        private String logo;
    }

}
