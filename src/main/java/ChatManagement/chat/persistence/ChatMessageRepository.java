package ChatManagement.chat.persistence;

import ChatManagement.chat.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomId(Long roomId);
}
