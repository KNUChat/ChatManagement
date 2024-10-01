package ChatManagement.chat.persistence;

import ChatManagement.chat.domain.Room;
import ChatManagement.chat.domain.status.RoomStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<Room, Long> {
    int countRoomsByMentorIdAndRoomStatus(Long mentorId, RoomStatus roomStatus);

    @Query("""
                    SELECT c
                    FROM Room c
                    JOIN FETCH Message m ON c.roomId = m.roomId
                    WHERE c.mentorId = :mentorId
                    AND c.roomStatus = :roomStatus
            """
    )
    List<Room> findRoomsByMentorIdAndRoomStatus(Long mentorId, RoomStatus roomStatus, Pageable limit);

    @Query("SELECT c FROM Room c WHERE c.menteeId = :id OR c.mentorId = :id")
    List<Room> findChatRoomsByParticipantId(Long id);
}
