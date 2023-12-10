package ChatManagement.auth.error;

import ChatManagement.global.execption.ChatManagementException;
import org.springframework.http.HttpStatus;

public class JwtSecurityException extends ChatManagementException {

    public JwtSecurityException() {
        super("서버 내부 오류. JWT를 처리할 수 없습니다.", HttpStatus.UNAUTHORIZED);
    }
}