package ChatManagement.global.execption;

import org.springframework.http.HttpStatus;

public class NotFoundChatRoomException extends ChatManagementException{
    public NotFoundChatRoomException(){
        super("채팅룸을 찾을 수 없습니다", HttpStatus.BAD_REQUEST);
    }
}
