package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.domain.status.RoomStatus;
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
