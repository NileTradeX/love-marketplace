package com.love.mq.message;

import com.love.mq.enums.GoodsOperationType;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsUpdateMessage implements Serializable {
    protected Long id;
    protected GoodsOperationType goodsOperationType;
}
