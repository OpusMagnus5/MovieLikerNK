package pl.damian.bodzioch.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.damian.bodzioch.controller.dto.BaseResponse;

import java.util.Locale;

@Order(2)
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GeneralControllerAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponse> handleAppException(Exception ex) {
        log.error("An unexpected Error occurred.", ex);
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("general.error", new Object[0], locale);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(message));
    }
}
