package ChatManagement.global.execption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChatManagementException extends RuntimeException{
    private final HttpStatus httpStatus;
    public ChatManagementException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
    public ChatManagementException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
