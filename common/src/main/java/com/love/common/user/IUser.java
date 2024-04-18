package com.love.common.user;

import java.io.Serializable;

public interface IUser extends Serializable {

    Long getId();

    String getUid();

    String getName();

    Integer getType();
}
