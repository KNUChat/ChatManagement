package ChatManagement.chat.repository;

import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.dao.RoomStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomsByMentorIdAndRoomStatus(Long mentorId, RoomStatus roomStatus);

    @Query("SELECT c FROM ChatRoom c WHERE c.menteeId = :id OR c.mentorId = :id")
    List<ChatRoom> findChatRoomsByParticipantId(Long id);
    ChatRoom findChatRoomsByRoomId(Long roomId);
}
