package ChatManagement.chat.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    private Long senderId;
    private Long receiverId;
    private String message;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom chatRoom;

    public void activateMessage() {
        this.sendTime = LocalDateTime.now();
    }

    private LocalDateTime sendTime;

}
