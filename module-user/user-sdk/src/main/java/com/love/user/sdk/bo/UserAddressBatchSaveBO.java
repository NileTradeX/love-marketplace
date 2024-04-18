package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tony
 * 2023/4/27
 */
@Data
public class UserAddressBatchSaveBO implements Serializable {
    private List<UserAddressSaveBO> addressList;
}
