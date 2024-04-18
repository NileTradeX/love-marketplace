package com.love.common.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "IdsParam")
public class IdsParam implements Serializable {

    @Schema(description = "idStr", requiredMode = Schema.RequiredMode.AUTO, example = "1,2,3")
    private String idStr;

    @Schema(description = "idList", requiredMode = Schema.RequiredMode.AUTO)
    private List<Long> idList;

    public List<Long> getIdList() {
        if (Objects.isNull(idList) && Objects.nonNull(idStr)) {
            return Arrays.stream(idStr.split(",")).filter(Objects::nonNull).map(Long::parseLong).collect(Collectors.toList());
        }
        return idList;
    }

    public String getIdStr() {
        if (Objects.isNull(idStr) && Objects.nonNull(idList) && !idList.isEmpty()) {
            return idList.stream().map(Object::toString).collect(Collectors.joining(","));
        }
        return idStr;
    }
}
