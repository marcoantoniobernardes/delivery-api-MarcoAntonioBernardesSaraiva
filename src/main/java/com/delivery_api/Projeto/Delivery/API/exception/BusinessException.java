package com.delivery_api.Projeto.Delivery.API.exception;

public class BusinessException extends RuntimeException {

    // ⬅️ SOLUÇÃO: DECLARAR O CAMPO AQUI
    private String errorCode;

    // Construtor Básico (Herda de RuntimeException)
    public BusinessException(String message) {
        super(message);
    }

    // Construtor com Causa (Herda de RuntimeException)
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    // Construtor com ErrorCode
    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String usuárioInválido, String s) {
    }

    // Getter para ErrorCode
    public String getErrorCode() {
        return errorCode;
    }

    // Setter para ErrorCode (Opcional, mas útil)
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}