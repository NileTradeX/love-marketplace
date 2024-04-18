package com.love.common.param;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(title = "PageParam")
public class PageParam implements Serializable {
    /**
     * 当前页码
     */
    @Schema(description = "pageNum", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", defaultValue = "1")
    private int pageNum = 1;
    /**
     * 每页大小
     */
    @Schema(description = "pageSize", requiredMode = Schema.RequiredMode.REQUIRED, example = "10", defaultValue = "10")
    private int pageSize = 10;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.min(pageSize, 50);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum <= 0 ? 1 : pageNum;
    }
}
