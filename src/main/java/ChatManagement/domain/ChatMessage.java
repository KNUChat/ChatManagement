package ChatManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage  {
    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String message;
}
