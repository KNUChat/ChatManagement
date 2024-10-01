package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.application.dto.RoomInfo;
import ChatManagement.chat.domain.status.RoomStatus;

public record ChatRoomResponse(
        Long roomId,
        Long mentorId,
        Long menteeId,
        RoomStatus roomStatus
) {
    public static ChatRoomResponse from(RoomInfo room) {
        return new ChatRoomResponse(
                room.roomId(),
                room.mentorId(),
                room.menteeId(),
                room.roomStatus()
        );
    }
}
