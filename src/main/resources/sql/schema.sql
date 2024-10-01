create table message
(
    chat_message_id   bigint auto_increment
        primary key,
    receiver_id       bigint                  null,
    room_id           bigint                  null,
    send_time         datetime(6)             null,
    sender_id         bigint                  null,
    chat_message_type enum ('NOTICE', 'TEXT') null,
    message           varchar(255)            null
);

create table room
(
    mentee_id   bigint                                                                                 null,
    mentor_id   bigint                                                                                 null,
    room_id     bigint auto_increment
        primary key,
    room_status enum ('CHAT_BLOCKED', 'CHAT_DELETED', 'CHAT_ENDED', 'CHAT_PROCEEDING', 'CHAT_WAITING') null
);

CREATE INDEX idx_room_send_time ON message (room_id, send_time);