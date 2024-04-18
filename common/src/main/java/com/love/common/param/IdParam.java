package com.love.common.param;

import com.love.common.exception.BizException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "IdParam")
public class IdParam implements Serializable {

    @Schema(description = "id", requiredMode = AUTO, example = "1")
    private Long id;

    public void setId(Long id) {
        if (Objects.isNull(id)) {
            throw BizException.build("id cannot be null");
        }
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
