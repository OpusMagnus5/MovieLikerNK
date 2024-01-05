package pl.damian.bodzioch.configuration;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.damian.bodzioch.controller.dto.BaseResponse;
import pl.damian.bodzioch.exception.AppException;

import java.util.Locale;

@RestControllerAdvice
@AllArgsConstructor
public class CustomControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({AppException.class})
    public ResponseEntity<BaseResponse> handleAppException(AppException ex, WebRequest webRequest) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ex.getMessage(), ex.getMessageParams().toArray(), locale);

        return ResponseEntity.status(ex.getHttpStatus()).body(new BaseResponse(message));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ex.getConstraintViolations().iterator().next().getMessage(), new Object[0], locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(message));
    }
}
