package ChatManagement.chat.application;

import ChatManagement.chat.domain.Room;
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
    public Room reserve(ChatRoomRequest request){
        Room room = chatRoomRepository.save(Room.builder()
                .menteeId(request.getMenteeId())
                .mentorId(request.getMentorId())
                .roomStatus(RoomStatus.CHAT_WAITING)
                .build());
        log.info("saved chatRoom: " + room);

        return room;
    }

    @Transactional
    public void activateChatRoom(Long mentorId){
        List<Room> processingRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_PROCEEDING);
        log.info("Processing Chat Room: " + processingRooms);
        List<Room> waitingRooms = chatRoomRepository
                .findChatRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_WAITING);

        for(int activateChatRoomIndex = 0;
            activateChatRoomIndex < Math.min(10 - processingRooms.size(), waitingRooms.size());
            activateChatRoomIndex++){

            waitingRooms.get(activateChatRoomIndex).activateRoom();
            chatMessageService.activateChatMessage(
                    waitingRooms.get(activateChatRoomIndex).getMessages());
            log.info("activated chatRoom : " + waitingRooms.get(activateChatRoomIndex));
        }
    }

    @Transactional
    public ChatRoomResponse endRoom
            (Long roomId){
        Room room =
                chatRoomRepository.findChatRoomByRoomId(roomId);
        if(room == null){
            throw new NotFoundChatRoomException();
        }
        room.endRoom();
        return ChatRoomResponse.from(room);

    }

    @Transactional
    public ChatRoomResponse deleteRoom
            (Long roomId){
        Room room =
                chatRoomRepository.findChatRoomByRoomId(roomId);
        if(room == null){
            throw new NotFoundChatRoomException();
        }
        room.deleteRoom();
        return ChatRoomResponse.from(room);

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
