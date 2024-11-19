package ChatManagement.chat.persistence;

import ChatManagement.chat.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByRoomIdOrderBySendTimeAsc(Long roomId, Pageable pageable);
}
