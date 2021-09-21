package com.example.anonymousquestionboxserver.model.dto;


import com.example.anonymousquestionboxserver.common.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    Integer responseCode;
    String message;
    Object data;

    public ResultDTO(ResultEnum result) {
        setResultEnum(result);
    }

    public void setResultEnum(ResultEnum result) {

        this.responseCode = result.getResponseCode();
        this.message = result.getMessage();
    }
}
