package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendBO implements Serializable {
    private String toEmail;
    private String subject;
    private String templateAlias;
    private Map<String, Object> templateModel;
}
