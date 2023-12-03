package ChatManagement.chat.repository;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.dao.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
