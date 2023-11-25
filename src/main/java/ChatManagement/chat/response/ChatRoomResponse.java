package ChatManagement.chat.response;

import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.dao.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class ChatRoomResponse {
    private Long mentorId;
    private Long menteeId;
    private RoomStatus roomStatus;

    public static ChatRoomResponse from(ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .menteeId(chatRoom.getMenteeId())
                .mentorId(chatRoom.getMentorId())
                .roomStatus(chatRoom.getRoomStatus())
                .build();
    }
}
