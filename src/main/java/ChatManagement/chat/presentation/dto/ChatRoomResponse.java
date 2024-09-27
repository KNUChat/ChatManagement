package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.domain.ChatRoom;
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

    public static ChatRoomResponse from(ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .roomId(chatRoom.getRoomId())
                .menteeId(chatRoom.getMenteeId())
                .mentorId(chatRoom.getMentorId())
                .roomStatus(chatRoom.getRoomStatus())
                .build();
    }
}
