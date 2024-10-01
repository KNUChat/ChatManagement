package ChatManagement.chat.presentation;

import ChatManagement.chat.application.ChatRoomService;
import ChatManagement.chat.presentation.dto.ChatMessageResponse;
import ChatManagement.chat.presentation.dto.ChatRoomRequest;
import ChatManagement.chat.presentation.dto.ChatRoomResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<Boolean> reserve(@RequestBody ChatRoomRequest request) {
        var command = request.toCommand();
        chatRoomService.reserve(command);

        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(@RequestParam Long id) {
        var roomInfos = chatRoomService.getChatRoomsByUserId(id);
        List<ChatRoomResponse> responses = roomInfos.stream()
                .map(ChatRoomResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/end/{roomId}")
    public ResponseEntity<ChatRoomResponse> endRoom(@PathVariable Long roomId) {
        var infos = chatRoomService.endRoom(roomId);

        var response = ChatRoomResponse.from(infos);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/delete/{roomId}")
    public ResponseEntity<ChatRoomResponse> deleteRoom(@PathVariable Long roomId) {
        var infos = chatRoomService.deleteRoom(roomId);

        var response = ChatRoomResponse.from(infos);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{roomId}/logs")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessage(@PathVariable Long roomId) {
        var infos = chatRoomService.getAllMessageById(roomId);

        var response = infos.stream()
                .map(ChatMessageResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
