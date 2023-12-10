package ChatManagement.kafka.domain;

import ChatManagement.kafka.type.LogType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LogMessage {
    private Date time;
    private String service;
    private LogType type;
    private String logMessage;
    private Long roomId;
    private Long userId;

}
