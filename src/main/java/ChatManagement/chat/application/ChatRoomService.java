package ChatManagement.chat.application;

import ChatManagement.chat.application.command.CreateMessageCommand;
import ChatManagement.chat.application.command.CreateRoomCommand;
import ChatManagement.chat.application.dto.MessageInfo;
import ChatManagement.chat.application.dto.RoomInfo;
import ChatManagement.chat.domain.Room;
import ChatManagement.chat.domain.status.RoomStatus;
import ChatManagement.chat.persistence.ChatMessageRepository;
import ChatManagement.chat.persistence.ChatRoomRepository;
import ChatManagement.global.execption.NotFoundChatRoomException;
import ChatManagement.kafka.application.LogProducer;
import ChatManagement.kafka.application.dto.LogMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private static final int MAX_PROCESSING_ROOMS = 10;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final LogProducer logProducer;

    @Transactional
    public RoomInfo reserve(CreateRoomCommand command) {
        var room = Room.of(command.mentorId(), command.menteeId());
        chatRoomRepository.save(room);

        var message = command.toMessage(room.getRoomId());
        chatMessageRepository.save(message);

        this.activateChatRoom(command.mentorId());
        return RoomInfo.from(room);
    }

    @Transactional
    public void sendMessage(CreateMessageCommand command) {
        chatMessageRepository.findById(command.roomId())
                .orElseThrow(NotFoundChatRoomException::new);

        var message = command.toEntity();
        chatMessageRepository.save(message);
        logProducer.sendMessage(
                LogMessage.messageLogOf(message.getMessage(), message.getRoomId(), message.getSenderId()));
    }

    @Transactional
    public RoomInfo endRoom(Long roomId) {
        Room room = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);
        room.endRoom();
        this.activateChatRoom(room.getMentorId());

        return RoomInfo.from(room);

    }

    @Transactional
    public RoomInfo deleteRoom(Long roomId) {
        Room room = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        room.deleteRoom();
        return RoomInfo.from(room);

    }

    @Transactional(readOnly = true)
    public List<RoomInfo> getChatRoomsByUserId(Long userId) {
        return chatRoomRepository
                .findChatRoomsByParticipantId(userId)
                .stream()
                .map(RoomInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MessageInfo> getAllMessageById(Long roomId) {
        var messages = chatMessageRepository.findByRoomIdOrderBySendTimeDesc(roomId);
        return messages.stream()
                .map(MessageInfo::from)
                .collect(Collectors.toList());
    }


    private void activateChatRoom(Long mentorId) {
        var processingRoomsCount = chatRoomRepository
                .countRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_PROCEEDING);
        var activateCount = MAX_PROCESSING_ROOMS - processingRoomsCount;

        if (activateCount <= 0) {
            return;
        }

        var waitingRooms = chatRoomRepository
                .findRoomsByMentorIdAndRoomStatus(mentorId, RoomStatus.CHAT_WAITING, Pageable.ofSize(activateCount));
        waitingRooms.forEach(Room::activateRoom);

        //TODO: 메시지 전송 시간 변경
    }
}
