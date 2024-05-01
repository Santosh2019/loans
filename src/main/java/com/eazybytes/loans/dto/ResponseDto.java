package com.eazybytes.loans.dto;

public class ResponseDto {
    private String statusCode;
    private String errorMsg;

    public ResponseDto() {
    }

    public ResponseDto(String statusCode, String errorMsg) {
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getHttpStatus() {
        return errorMsg;
    }

    public void setHttpStatus(String httpStatus) {
        this.errorMsg = httpStatus;
    }


    @Override
    public String toString() {
        return "ResponseDto{" +
                "statusCode='" + statusCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }


}
