package com.love.marketplace.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BoltAddressDTO {
    @SerializedName("id")
    private String id;
    @SerializedName("street_address1")
    private String streetAddress1;
    @SerializedName("street_address2")
    private String streetAddress2;
    @SerializedName("street_address3")
    private String streetAddress3;
    @SerializedName("street_address4")
    private String streetAddress4;
    @SerializedName("locality")
    private String locality;
    @SerializedName("region")
    private String region;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("postal_code")
    private String postalCode;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("country")
    private String country;
    @SerializedName("name")
    private String name;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("company")
    private String company;
    @SerializedName(value = "phone", alternate = {"phone_number"})
    private String phone;
    @SerializedName(value = "email", alternate = {"email_address"})
    private String email;
    @SerializedName("default")
    private Boolean defaultX;
    @SerializedName("door_code")
    private Integer doorCode;
}
