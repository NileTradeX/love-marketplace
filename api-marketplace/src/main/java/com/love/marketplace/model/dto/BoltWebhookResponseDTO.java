package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BoltWebhookResponseDTO {
    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private ErrorDTO error;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ErrorDTO {
        @SerializedName("code")
        private Integer code;
        @SerializedName("message")
        private String message;
    }

    public BoltWebhookResponseDTO() {
        this.status = "succeeded";
    }

    public BoltWebhookResponseDTO(Integer code, String message) {
        this.status = "failed";
        this.error = new ErrorDTO(code,message);
    }
}
