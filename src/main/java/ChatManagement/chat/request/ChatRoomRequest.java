package ChatManagement.chat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatRoomRequest {
    private Long mentorId;
    private Long menteeId;
    private String message;

}
