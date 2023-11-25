package ChatManagement.chat.service;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.repository.ChatMessageRepository;
import ChatManagement.chat.response.ChatMessageResponse;
import ChatManagement.chat.response.ChatRoomResponse;
import ChatManagement.kafka.domain.KafkaMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveKafkaMessage(KafkaMessage kafkaMessage){
        chatMessageRepository.save(ChatMessage.builder()
                .roomId(kafkaMessage.getRoomId())
                .senderId(kafkaMessage.getSenderId())
                .receiverId(kafkaMessage.getReceiverId())
                .sendTime(kafkaMessage.getSendTime())
                .message(kafkaMessage.getMessage())
                .build());
    }

    public void saveChatMessage(ChatMessage chatMessage){
        chatMessageRepository.save(chatMessage);
    }

    public void activateChatMessage(Long roomId){
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByRoomId(roomId);
        for(ChatMessage chatMessage: chatMessages){
            if(chatMessage.getSendTime() == null){
                chatMessage.activateMessage();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getAllMessageById(Long roomId){
        return chatMessageRepository
                .findChatMessagesByRoomId(roomId)
                .stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
