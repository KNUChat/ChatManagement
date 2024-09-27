package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.domain.Room;
import ChatManagement.chat.domain.status.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class ChatRoomResponse {
    private Long roomId;
    private Long mentorId;
    private Long menteeId;
    private RoomStatus roomStatus;

    public static ChatRoomResponse from(Room room){
        return ChatRoomResponse.builder()
                .roomId(room.getRoomId())
                .menteeId(room.getMenteeId())
                .mentorId(room.getMentorId())
                .roomStatus(room.getRoomStatus())
                .build();
    }
}
