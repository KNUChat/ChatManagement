package ChatManagement.chat.repository;

import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.dao.RoomStatus;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomsByMentorIdAndRoomStatus(Long mentorId, RoomStatus roomStatus);
}
