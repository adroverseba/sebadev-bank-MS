package com.sebadevbank.transactions.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomFeignErrorDecoder implements ErrorDecoder{

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        // Si el servicio de Accounts devuelve un 404, lanza una excepción específica
        if (status == HttpStatus.NOT_FOUND) {
            return new ResourceNotFoundException("Cuenta no encontrada en el servicio de Accounts.");
        }

        // Si el error es 403, maneja ForbiddenException
        if (status == HttpStatus.FORBIDDEN) {
            return new ForbiddenException("No tienes permiso para acceder a esta cuenta.");
        }

        // Para otros errores, usa el manejador por defecto de Feign
        return defaultErrorDecoder.decode(methodKey, response);
    }

}