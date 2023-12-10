package ChatManagement.global.execption;

import org.springframework.http.HttpStatus;

public class NotAllowChangeRoomStatusException extends ChatManagementException {
    public NotAllowChangeRoomStatusException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
