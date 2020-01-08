package com.example.myFirstSpringApp.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.myFirstSpringApp.util.LoggerHelper.writeLog;

@RestControllerAdvice
public class GlobalExceptionHandler{


    @ExceptionHandler(Exception.class)
    public ResponseEntity globalExceptionsHandler(Exception exception, WebRequest request) throws Exception{

        if(exception.getClass().getSuperclass()!=CustomRunTimeException.class){
            Map<String, Object> response = new HashMap<>();
            response.put("message","Ooops!, something went wrong. Please try again, if the problem persist contact with support team");
            writeLog("--GlobalHandler--", exception).error("Unexpected error.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        throw exception;
    }


}
