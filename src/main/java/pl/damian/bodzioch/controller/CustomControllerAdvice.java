package pl.damian.bodzioch.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.damian.bodzioch.controller.dto.BaseResponse;
import pl.damian.bodzioch.exception.AppException;

import java.util.Locale;
import java.util.stream.Collectors;

@Order(1)
@RestControllerAdvice
@AllArgsConstructor
public class CustomControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({AppException.class})
    public ResponseEntity<BaseResponse> handleAppException(AppException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ex.getMessage(), ex.getMessageParams().toArray(), locale);

        return ResponseEntity.status(ex.getHttpStatus()).body(new BaseResponse(message));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .map(code -> messageSource.getMessage(code, new Object[0], locale))
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotEmpty)
                .map(code -> messageSource.getMessage(code, new Object[0], locale))
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
    }
}
