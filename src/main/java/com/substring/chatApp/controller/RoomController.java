package com.substring.chatApp.controller;


import com.substring.chatApp.config.AppConstants;
import com.substring.chatApp.entities.Message;
import com.substring.chatApp.entities.Room;
import com.substring.chatApp.repo.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(AppConstants.FRONT_END_USERBASE_URL)
public class RoomController {

    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    // create Room

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){

        if(roomRepository.findByRoomId(roomId) != null){
            // Room is already there
            return ResponseEntity.badRequest().body("Room Already exists!");
        }

        // create new room

        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

     // Get rooms : join

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        Room room = roomRepository.findByRoomId(roomId);

        if(room == null){
            return ResponseEntity.badRequest().body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    // get Messages of room

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
                                                     @RequestParam(value = "page", defaultValue = "0" ,required = false) int page,
                                                     @RequestParam(value = "size",defaultValue = "20",required = false) int size){
        Room room = roomRepository.findByRoomId(roomId);
        if(room==null){
            return ResponseEntity.badRequest().build();
        }

        // get messages

        //Pagination

        List<Message> messages = room.getMessages();

        int start = Math.max(0,messages.size() - (page+1) * size);
        int end = Math.min(messages.size(),start + size);
        List<Message> paginatedMessages =  messages.subList(start,end);
        return ResponseEntity.ok(paginatedMessages);
    }
}
