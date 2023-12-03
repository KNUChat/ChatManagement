package ChatManagement.global.execption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ChatManagementExceptionHandler {

    @ExceptionHandler(ChatManagementException.class)
    public ResponseEntity<ErrorResponse> handleChatManagementException(ChatManagementException e) {
        log.info("Exception check    : " + e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(
                        e.getHttpStatus().value(),
                        e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> requestParamException(HttpMessageNotReadableException e){
        log.info("Exception check  : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()));
    }
}
