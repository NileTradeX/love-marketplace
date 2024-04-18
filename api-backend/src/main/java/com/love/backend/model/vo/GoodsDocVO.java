package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDocVO implements Serializable {
    private String coa;
    @Schema(description = "status(0:close 1:open)")
    private Integer coaStatus;
    private String msds;
    @Schema(description = "status(0:close 1:open)")
    private Integer msdsStatus;
    private String ppp;
    @Schema(description = "status(0:close 1:open)")
    private Integer pppStatus;
    private String logfm;
    @Schema(description = "status(0:close 1:open)")
    private Integer logfmStatus;
    private String rein;
    @Schema(description = "status(0:close 1:open)")
    private Integer reinStatus;
}

