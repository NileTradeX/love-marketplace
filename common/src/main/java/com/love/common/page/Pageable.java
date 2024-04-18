package com.love.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pageable<T> implements Serializable {

    @Builder.Default
    protected long total = 0;
    @Builder.Default
    protected long pageSize = 10;
    @Builder.Default
    protected long pageNum = 1;
    @Builder.Default
    private List<T> records = new ArrayList<>();
    private long pages;

    public Pageable(long current, long size) {
        this.pageNum = current;
        this.pageSize = size;
    }

    public long getPages() {
        if (total <= 0) {
            return 0;
        }

        if (pageSize > 0) {
            return ( int ) ((total + pageSize - 1) / pageSize);
        }

        return 0;
    }
}
