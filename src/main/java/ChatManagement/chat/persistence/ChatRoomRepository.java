package ChatManagement.chat.persistence;

import ChatManagement.chat.domain.ChatRoom;
import ChatManagement.chat.domain.status.RoomStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomsByMentorIdAndRoomStatus(Long mentorId, RoomStatus roomStatus);

    @Query("SELECT c FROM ChatRoom c WHERE c.menteeId = :id OR c.mentorId = :id")
    List<ChatRoom> findChatRoomsByParticipantId(Long id);
    ChatRoom findChatRoomByRoomId(Long roomId);
}
