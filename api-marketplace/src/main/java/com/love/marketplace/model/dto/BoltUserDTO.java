package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tony
 * 2023/4/29
 */
@Data
public class BoltUserDTO implements Serializable {

    private static final long serialVersionUID = 98165573896690731L;
    /**
     * addresses
     */
    @SerializedName("addresses")
    private List<Addresses> addresses;
    /**
     * hasBoltAccount
     */
    @SerializedName("has_bolt_account")
    private Boolean hasBoltAccount;
    /**
     * profile
     */
    @SerializedName("profile")
    private Profile profile;

    /**
     * Profile
     */
    @Data
    public static class Profile implements Serializable {
        private static final long serialVersionUID = -4394898389343767388L;
        /**
         * email
         */
        @SerializedName("email")
        private String email;
        /**
         * firstName
         */
        @SerializedName("first_name")
        private String firstName;
        /**
         * lastName
         */
        @SerializedName("last_name")
        private String lastName;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * phone
         */
        @SerializedName("phone")
        private String phone;
    }

    /**
     * Addresses
     */
    @Data
    public static class Addresses implements Serializable {
        private static final long serialVersionUID = 7711073825102656487L;
        /**
         * company
         */
        @SerializedName("company")
        private String company;
        /**
         * country
         */
        @SerializedName("country")
        private String country;
        /**
         * countryCode
         */
        @SerializedName("country_code")
        private String countryCode;
        /**
         * emailAddress
         */
        @SerializedName("email_address")
        private String emailAddress;
        /**
         * firstName
         */
        @SerializedName("first_name")
        private String firstName;
        /**
         * id
         */
        @SerializedName("id")
        private String id;
        /**
         * lastName
         */
        @SerializedName("last_name")
        private String lastName;
        /**
         * locality
         */
        @SerializedName("locality")
        private String locality;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * phoneNumber
         */
        @SerializedName("phone_number")
        private String phoneNumber;
        /**
         * postalCode
         */
        @SerializedName("postal_code")
        private String postalCode;
        /**
         * region
         */
        @SerializedName("region")
        private String region;
        /**
         * regionCode
         */
        @SerializedName("region_code")
        private String regionCode;
        /**
         * streetAddress1
         */
        @SerializedName("street_address1")
        private String streetAddress1;
        /**
         * streetAddress2
         */
        @SerializedName("street_address2")
        private String streetAddress2;
        /**
         * streetAddress3
         */
        @SerializedName("street_address3")
        private String streetAddress3;
        /**
         * streetAddress4
         */
        @SerializedName("street_address4")
        private String streetAddress4;
        /**
         * doorCode
         */
        @SerializedName("door_code")
        private Integer doorCode;
        /**
         * defaultX
         */
        @SerializedName("default")
        private Boolean defaultX;
    }
}
