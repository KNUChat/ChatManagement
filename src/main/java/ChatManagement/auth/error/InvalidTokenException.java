package ChatManagement.auth.error;

import ChatManagement.global.execption.ChatManagementException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ChatManagementException {
    public InvalidTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}