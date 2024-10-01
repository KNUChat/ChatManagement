package ChatManagement.chat.application.command;

import ChatManagement.chat.domain.Message;
import ChatManagement.chat.domain.Room;

public record CreateRoomCommand(
        Long mentorId,
        Long menteeId,
        String message
) {
    public Room toRoom() {
        return Room.of(mentorId, menteeId);
    }

    public Message toMessage(Long roomId) {
        return Message.of(mentorId, menteeId, message, roomId);
    }
}
