package com.love.influencer.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfUserHitsSaveBO implements Serializable {
    private Long influencerId;
    private String ip;
    private LocalDateTime createTime;
}

