package ChatManagement.chat.application;

import ChatManagement.chat.domain.ChatMessage;
import ChatManagement.chat.domain.ChatRoom;
import ChatManagement.chat.repository.ChatMessageRepository;
import ChatManagement.chat.repository.ChatRoomRepository;
import ChatManagement.chat.presentation.dto.ChatRoomRequest;
import ChatManagement.chat.presentation.dto.ChatMessageResponse;
import ChatManagement.global.execption.NotFoundChatRoomException;
import ChatManagement.kafka.service.dto.KafkaMessage;
import ChatManagement.kafka.service.dto.LogMessage;
import ChatManagement.kafka.service.KafkaService;
import ChatManagement.kafka.service.dto.type.LogType;
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
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByRoomId(kafkaMessage.getRoomId());
        if(chatRoom == null){
            throw new NotFoundChatRoomException();
        }
        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder()
                .chatRoom(chatRoom)
                .senderId(kafkaMessage.getSenderId())
                .receiverId(kafkaMessage.getReceiverId())
                .sendTime(kafkaMessage.getSendTime())
                .message(kafkaMessage.getMessage())
                .chatMessageType(kafkaMessage.getChatMessageType())
                .build());
        sendLogInfo(chatRoom, chatMessage);
    }
    @Transactional
    public void reserve(ChatRoomRequest request, ChatRoom chatRoom){
        ChatMessage savedMessage = chatMessageRepository.save(ChatMessage.builder()
                        .receiverId(request.getMentorId())
                        .senderId(request.getMenteeId())
                        .message(request.getMessage())
                        .sendTime(null)
                        .build());
        chatRoom.initChatMessage(savedMessage);
        log.info("save chatMessage: " + savedMessage);
        sendLogInfo(chatRoom, savedMessage);
    }

    public void activateChatMessage(List<ChatMessage> chatMessages){
        for(ChatMessage chatMessage: chatMessages){
            if(chatMessage.getSendTime() == null){
                chatMessage.activateMessage();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getAllMessageById(Long roomId){
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
        if(chatRoom.isEmpty()){
            throw new NotFoundChatRoomException();
        }
        return chatRoom
                .get().getChatMessages()
                .stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private void sendLogInfo(ChatRoom chatRoom, ChatMessage chatMessage){
        kafkaService.sendMessage(
                LogMessage.builder()
                        .roomId(chatRoom.getRoomId())
                        .logMessage("Saved Message : " + chatMessage.getMessage())
                        .service("Chat-Management")
                        .type(LogType.INFO)
                        .time(new Date())
                        .userId(chatMessage.getSenderId())
                        .build()
        );
    }
}
