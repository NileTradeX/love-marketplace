package com.love.marketplace.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(value= JsonInclude.Include.NON_EMPTY)
public class BoltEventResponseDTO<T extends BoltEventDTO> {
    @JsonProperty("event")
    private String event;
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private T data;
    @JsonProperty("errors")
    private List<ErrorDTO> errors;

    @NoArgsConstructor
    @Data
    public static class ErrorDTO {
        @SerializedName("code")
        private Integer code;
        @SerializedName("message")
        private String message;
    }

    public BoltEventResponseDTO(String event,int errorCode,String errorMessage){
        this.event = event;
        this.status = "failure";
        List<ErrorDTO> errorDTOS = new ArrayList<>(1);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(errorCode);
        errorDTO.setMessage(errorMessage);
        errorDTOS.add(errorDTO);
        this.errors = errorDTOS;
    }

    public BoltEventResponseDTO(String event,T data){
        this.event = event;
        this.status = "success";
        this.data = data;
    }
}
