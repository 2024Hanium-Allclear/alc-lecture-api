package com.allcear.alclectureapi.common.apiPayload.code.status;
import com.allcear.alclectureapi.common.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();

    ApiResponse<Void> getErrorResponse();
}
