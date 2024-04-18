package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Tony
 * 2023/4/29
 */
@Data
public class BoltAuthDTO implements Serializable {

    private static final long serialVersionUID = -444889038118512003L;
    /**
     * accessToken
     */
    @SerializedName("access_token")
    private String accessToken;
    /**
     * expiresIn
     */
    @SerializedName("expires_in")
    private Integer expiresIn;
    /**
     * idToken
     */
    @SerializedName("id_token")
    private String idToken;
    /**
     * refreshToken
     */
    @SerializedName("refresh_token")
    private String refreshToken;
    /**
     * refreshTokenScope
     */
    @SerializedName("refresh_token_scope")
    private String refreshTokenScope;
    /**
     * scope
     */
    @SerializedName("scope")
    private String scope;
    /**
     * tokenType
     */
    @SerializedName("token_type")
    private String tokenType;
}
