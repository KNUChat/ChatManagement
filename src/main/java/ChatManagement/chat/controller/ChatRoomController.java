package ChatManagement.chat.controller;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.request.ChatRoomRequest;
import ChatManagement.chat.response.ChatMessageResponse;
import ChatManagement.chat.response.ChatRoomResponse;
import ChatManagement.chat.service.ChatMessageService;
import ChatManagement.chat.service.ChatRoomService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<Boolean> reserve(
            @RequestBody ChatRoomRequest request
    ){
        log.info(request.toString());
        chatMessageService.reserve(request, chatRoomService.reserve(request));
        chatRoomService.activateChatRoom(request.getMentorId());
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(
            @RequestParam Long id
    ){
        List<ChatRoomResponse> responses = chatRoomService.getChatRoomById(id);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{roomId}/logs")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessage(
            @PathVariable Long roomId
    ){
        List<ChatMessageResponse> response = chatMessageService.getAllMessageById(roomId);
        return ResponseEntity.ok(response);
    }
}
