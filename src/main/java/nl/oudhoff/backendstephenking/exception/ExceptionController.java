package nl.oudhoff.backendstephenking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> exception (MethodArgumentNotValidException exception){
        return new ResponseEntity<>(exception.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST );
    }
}

