package com.allcear.alclectureapi.fileupload.exception;


import com.allcear.alclectureapi.common.apiPayload.exception.GeneralException;
import com.allcear.alclectureapi.fileupload.enums.FileUploadErrorCode;

public class EmptyFileExceptionHandler extends GeneralException {
    public EmptyFileExceptionHandler(FileUploadErrorCode errorCode) {
        super(errorCode);
    }
}
