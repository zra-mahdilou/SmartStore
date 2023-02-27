package com.example.smartstore.execption;

import com.example.smartstore.component.MessageComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class StoreExceptionHandler {
    private final MessageComponent messageComponent;

    public StoreExceptionHandler(MessageComponent messageComponent) {
        this.messageComponent = messageComponent;
    }

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<RestMessage> handleAuthenticationException(StoreException ex) {
        String source = messageComponent.getMessage(ex.getMessage(), ex.getArgs());
        return new ResponseEntity<RestMessage>(new RestMessage(source), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
