package ChatManagement.chat.service;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.dao.RoomStatus;
import ChatManagement.chat.repository.ChatMessageRepository;
import ChatManagement.chat.repository.ChatRoomRepository;
import ChatManagement.chat.request.ChatRoomRequest;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j @Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;

    @Transactional
    public Long reserve(ChatRoomRequest request){
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .menteeId(request.getMenteeId())
                .mentorId(request.getMentorId())
                .roomStatus(RoomStatus.CHAT_WAITING)
                .build());
        log.info("saved chatRoom: " + chatRoom.toString());
        chatMessageService.saveChatMessage(ChatMessage.builder()
                .receiverId(request.getMentorId())
                .senderId(request.getMenteeId())
                .roomId(chatRoom.getRoomId())
                .message(request.getMessage())
                .sendTime(null)
                .build());
        return chatRoom.getMentorId();
    }

    @Transactional
    public void activateChatRoom(Long mentorId){
        List<ChatRoom> processingChatRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_PROCEEDING);
        List<ChatRoom> waitingChatRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_WAITING);

        for(int activateChatRoomIndex = 0;
            activateChatRoomIndex < Math.min(10 -processingChatRooms.size(), waitingChatRooms.size());
            activateChatRoomIndex++){
            waitingChatRooms.get(activateChatRoomIndex).activateRoom();
            log.info("activated chatRoom : " + waitingChatRooms.get(activateChatRoomIndex));
        }
    }

}
