package ChatManagement.chat.application;

import ChatManagement.chat.domain.ChatRoom;
import ChatManagement.global.execption.NotFoundChatRoomException;
import ChatManagement.chat.domain.status.RoomStatus;
import ChatManagement.chat.persistence.ChatRoomRepository;
import ChatManagement.chat.presentation.dto.ChatRoomRequest;
import ChatManagement.chat.presentation.dto.ChatRoomResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    @Transactional
    public ChatRoomResponse endRoom
            (Long roomId){
        ChatRoom chatRoom =
                chatRoomRepository.findChatRoomByRoomId(roomId);
        if(chatRoom == null){
            throw new NotFoundChatRoomException();
        }
        chatRoom.endRoom();
        return ChatRoomResponse.from(chatRoom);

    }

    @Transactional
    public ChatRoomResponse deleteRoom
            (Long roomId){
        ChatRoom chatRoom =
                chatRoomRepository.findChatRoomByRoomId(roomId);
        if(chatRoom == null){
            throw new NotFoundChatRoomException();
        }
        chatRoom.deleteRoom();
        return ChatRoomResponse.from(chatRoom);

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
