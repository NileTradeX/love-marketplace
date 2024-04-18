package com.love.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttrNameDTO implements Serializable {
    private Long id;
    private String name;
    private Set<AttrValueDTO> values;
}
