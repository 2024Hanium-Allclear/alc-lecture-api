package com.allcear.alclectureapi.fileupload.exception;


import com.allcear.alclectureapi.common.apiPayload.exception.GeneralException;
import com.allcear.alclectureapi.fileupload.enums.FileUploadErrorCode;
public class KafkaConsumerExceptionHandler extends GeneralException {


    public KafkaConsumerExceptionHandler(FileUploadErrorCode errorCode) {
        super(errorCode);
    }
}
