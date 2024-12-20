package com.allcear.alclectureapi.common.apiPayload.exception;

import com.allcear.alclectureapi.common.apiPayload.code.status.BaseErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public GeneralException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
