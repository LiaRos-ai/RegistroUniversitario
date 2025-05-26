package com.universidad.validation;

/**
 * Excepción de negocio reutilizable para validaciones de lógica de aplicación.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
