package ChatManagement.chat.application.dto;

import ChatManagement.chat.domain.Room;
import ChatManagement.chat.domain.status.RoomStatus;

public record RoomInfo(
        Long roomId,
        Long mentorId,
        Long menteeId,
        RoomStatus roomStatus
) {
    public static RoomInfo from(Room room) {
        return new RoomInfo(room.getRoomId(), room.getMentorId(), room.getMenteeId(), room.getRoomStatus());
    }
}
