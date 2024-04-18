package com.love.influencer.backend.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InfUserQueryByCodeParam implements Serializable {

    @NotNull(message = "code cannot be null")
    private String code;
}
