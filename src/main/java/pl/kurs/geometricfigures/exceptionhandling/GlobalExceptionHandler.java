package pl.kurs.geometricfigures.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.geometricfigures.exceptions.BadEntityException;
import pl.kurs.geometricfigures.exceptions.BadIdException;
import pl.kurs.geometricfigures.exceptions.BadRequestException;
import pl.kurs.geometricfigures.exceptions.SpecificEntityNotFoundException;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadIdException.class, BadEntityException.class})
    public ResponseEntity<ExceptionResponseDto> handleEntityNotFoundException(Exception e) {
        ExceptionResponseDto response = new ExceptionResponseDto(List.of(e.getMessage()), "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SpecificEntityNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponseDto> handleSpecificEntityNotFoundException(Exception e) {
        ExceptionResponseDto response = new ExceptionResponseDto(List.of("Entity not found"), "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getFieldErrors().stream()
                .map(fe -> "field: " + fe.getField() + " / rejected value: '" + fe.getRejectedValue() + "' / message: " + fe.getDefaultMessage())
                .collect(Collectors.toList());
        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(BadRequestException e) {
        ExceptionResponseDto response = new ExceptionResponseDto(List.of(e.getMessage()), "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleClassNotFoundException(ClassNotFoundException e) {
        ExceptionResponseDto response = new ExceptionResponseDto(List.of("Class not found exception", e.getMessage()), "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
