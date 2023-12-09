package ChatManagement.chat.request;

import ChatManagement.global.status.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomPatchRequest {
    private Long roomId;
    private RoomStatus roomStatus;
}
