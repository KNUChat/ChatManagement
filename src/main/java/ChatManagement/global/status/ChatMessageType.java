package ChatManagement.global.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatMessageType {
    TEXT("TEXT"),
    NOTICE("NOTICE");

    private final String type;
}