package ChatManagement.chat.repository;

import ChatManagement.chat.dao.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findChatMessagesByRoomId(Long roomId);
}
