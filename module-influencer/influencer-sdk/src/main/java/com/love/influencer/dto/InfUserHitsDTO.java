package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class InfUserHitsDTO implements Serializable {
    private Long id;
    private Long influencerId;
    private String ip;
    private LocalDateTime createTime;
}
