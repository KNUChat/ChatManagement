package ChatManagement.chat.application;

import ChatManagement.chat.domain.Message;
import ChatManagement.chat.domain.Room;
import ChatManagement.chat.persistence.ChatMessageRepository;
import ChatManagement.chat.persistence.ChatRoomRepository;
import ChatManagement.chat.presentation.dto.ChatRoomRequest;
import ChatManagement.chat.presentation.dto.ChatMessageResponse;
import ChatManagement.global.execption.NotFoundChatRoomException;
import ChatManagement.kafka.application.dto.KafkaMessage;
import ChatManagement.kafka.application.dto.LogMessage;
import ChatManagement.kafka.application.KafkaService;
import ChatManagement.kafka.application.dto.type.LogType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final KafkaService kafkaService;

    @Transactional
    @KafkaListener(topics = "chatlog", errorHandler = "kafkaListenerErrorHandler")
    public void saveKafkaMessage(KafkaMessage kafkaMessage){
        Room room = chatRoomRepository.findChatRoomByRoomId(kafkaMessage.getRoomId());
        if(room == null){
            throw new NotFoundChatRoomException();
        }
        Message message = chatMessageRepository.save(Message.builder()
                .room(room)
                .senderId(kafkaMessage.getSenderId())
                .receiverId(kafkaMessage.getReceiverId())
                .sendTime(kafkaMessage.getSendTime())
                .message(kafkaMessage.getMessage())
                .chatMessageType(kafkaMessage.getChatMessageType())
                .build());
        sendLogInfo(room, message);
    }
    @Transactional
    public void reserve(ChatRoomRequest request, Room room){
        Message savedMessage = chatMessageRepository.save(Message.builder()
                        .receiverId(request.getMentorId())
                        .senderId(request.getMenteeId())
                        .message(request.getMessage())
                        .sendTime(null)
                        .build());
        room.initChatMessage(savedMessage);
        log.info("save chatMessage: " + savedMessage);
        sendLogInfo(room, savedMessage);
    }

    public void activateChatMessage(List<Message> messages){
        for(Message message : messages){
            if(message.getSendTime() == null){
                message.activateMessage();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getAllMessageById(Long roomId){
        Optional<Room> chatRoom = chatRoomRepository.findById(roomId);
        if(chatRoom.isEmpty()){
            throw new NotFoundChatRoomException();
        }
        return chatRoom
                .get().getMessages()
                .stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private void sendLogInfo(Room room, Message message){
        kafkaService.sendMessage(
                LogMessage.builder()
                        .roomId(room.getRoomId())
                        .logMessage("Saved Message : " + message.getMessage())
                        .service("Chat-Management")
                        .type(LogType.INFO)
                        .time(new Date())
                        .userId(message.getSenderId())
                        .build()
        );
    }
}
