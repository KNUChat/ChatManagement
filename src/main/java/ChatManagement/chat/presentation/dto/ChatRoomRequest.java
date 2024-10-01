package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.application.command.CreateRoomCommand;

public record ChatRoomRequest(
        Long mentorId,
        Long menteeId,
        String message
) {
    public CreateRoomCommand toCommand() {
        return new CreateRoomCommand(mentorId, menteeId, message);
    }
}
