package com.substring.chatApp.entities;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    private String sender;
    private String content;
    private LocalDateTime timeStamp;

//    public Message(String sender, String content){
//        this.sender = sender;
//        this.content = content;
//        this.timeStamp = LocalDateTime.now();
//    }

//    public void setContent(String content) {
//    }
//
//    public void setSender(String sender) {
//    }
//
//    public void setTimeStamp(LocalDateTime now) {
//    }
}
