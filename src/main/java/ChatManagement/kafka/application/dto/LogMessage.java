package ChatManagement.kafka.application.dto;

import ChatManagement.kafka.application.dto.type.LogType;
import java.util.Date;

public record LogMessage(
        Date time,
        String service,
        LogType type,
        String logMessage,
        Long roomId,
        Long userId
) {
    public static LogMessage messageLogOf(String chatMessage, Long roomId, Long senderId) {
        return new LogMessage(new Date(), "Chat-Management", LogType.INFO, chatMessage, roomId, senderId);
    }
}
