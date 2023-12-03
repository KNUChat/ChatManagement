package ChatManagement.chat.service;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.dao.ChatRoom;
import ChatManagement.chat.repository.ChatMessageRepository;
import ChatManagement.chat.repository.ChatRoomRepository;
import ChatManagement.chat.request.ChatRoomRequest;
import ChatManagement.chat.response.ChatMessageResponse;
import ChatManagement.global.execption.NotFoundChatRoomException;
import ChatManagement.kafka.domain.KafkaMessage;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.quota.ClientQuotaAlteration.Op;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    public void saveKafkaMessage(KafkaMessage kafkaMessage){
        chatMessageRepository.save(ChatMessage.builder()
                .chatRoom(
                        chatRoomRepository.findChatRoomsByRoomId(kafkaMessage.getRoomId())
                )
                .senderId(kafkaMessage.getSenderId())
                .receiverId(kafkaMessage.getReceiverId())
                .sendTime(kafkaMessage.getSendTime())
                .message(kafkaMessage.getMessage())
                .build());
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
}
