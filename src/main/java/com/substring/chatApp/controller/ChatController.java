package com.substring.chatApp.controller;


import com.substring.chatApp.config.AppConstants;
import com.substring.chatApp.entities.Message;
import com.substring.chatApp.entities.Room;
import com.substring.chatApp.playload.MessageRequest;
import com.substring.chatApp.repo.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(AppConstants.FRONT_END_USERBASE_URL)
public class ChatController {

    private RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    // For Sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}") // app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}") // subscribe
    public Message sendMessage(
            @DestinationVariable String roomId,
            MessageRequest request
            //@RequestBody MessageRequest request

            ){
        Room room = roomRepository.findByRoomId(request.getRoomId());
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if(room != null){
            room.getMessages().add(message);
            roomRepository.save(room);
        }else{
            throw new RuntimeException("room not found");
        }
        return message;
    }
}
