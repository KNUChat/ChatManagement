package ChatManagement.chat.controller;

import ChatManagement.chat.request.ChatRoomRequest;
import ChatManagement.chat.response.ChatRoomResponse;
import ChatManagement.chat.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    public ResponseEntity<Boolean> reserve(
            @RequestBody ChatRoomRequest request
    ){
        log.info(request.toString());
        Long mentorId = chatRoomService.reserve(request);

        chatRoomService.activateChatRoom(mentorId);
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(@RequestParam Long id){
        List<ChatRoomResponse> responses = chatRoomService.getChatRoomById(id);
        return ResponseEntity.ok(responses);
    }
}
