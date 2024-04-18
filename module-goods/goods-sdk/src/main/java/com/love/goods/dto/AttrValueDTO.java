package com.love.goods.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AttrValueDTO implements Serializable {
    private Long id;
    private Long attrNameId;
    private String value;
    private Integer sortNum;
}
