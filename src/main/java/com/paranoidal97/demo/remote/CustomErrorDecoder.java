package com.paranoidal97.demo.remote;

import com.paranoidal97.demo.exception.BadRequestException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.Date;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        int status = response.status();
        Date retryAfter = response.headers().containsKey("Retry-After")
                ? new Date(System.currentTimeMillis() +  1000)
                : null;
        switch(status){
            case 504:
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        null,
                        response.request());
            case 404:
                throw new DataNotFoundException(response.reason());
            case 400:
                throw new BadRequestException(response.reason());
            default:
                return exception;
        }
    }
}
