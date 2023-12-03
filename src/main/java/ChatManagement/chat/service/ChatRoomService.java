package ChatManagement.chat.service;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.dao.RoomStatus;
import ChatManagement.chat.repository.ChatRoomRepository;
import ChatManagement.chat.request.ChatRoomRequest;
import ChatManagement.chat.response.ChatRoomResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;

    @Transactional
    public ChatRoom reserve(ChatRoomRequest request){
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .menteeId(request.getMenteeId())
                .mentorId(request.getMentorId())
                .roomStatus(RoomStatus.CHAT_WAITING)
                .build());
        log.info("saved chatRoom: " + chatRoom);

        return chatRoom;
    }

    @Transactional
    public void activateChatRoom(Long mentorId){
        List<ChatRoom> processingChatRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_PROCEEDING);
        log.info("Processing Chat Room: " + processingChatRooms);
        List<ChatRoom> waitingChatRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_WAITING);

        for(int activateChatRoomIndex = 0;
            activateChatRoomIndex < Math.min(10 -processingChatRooms.size(), waitingChatRooms.size());
            activateChatRoomIndex++){

            waitingChatRooms.get(activateChatRoomIndex).activateRoom();
            chatMessageService.activateChatMessage(
                    waitingChatRooms.get(activateChatRoomIndex).getChatMessages());
            log.info("activated chatRoom : " + waitingChatRooms.get(activateChatRoomIndex));
        }
    }

    @Transactional(readOnly=true)
    public List<ChatRoomResponse> getChatRoomById(Long id) {
        return chatRoomRepository
                .findChatRoomsByParticipantId(id)
                .stream()
                .map(ChatRoomResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
