package com.example.mysql.controllers

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("messages")
class MessageController {

    @GetMapping
    fun index(): List<Message> {
        return listOf(
            Message(1, "test message1"),
            Message(2, "test message2"),
            Message(3, "test message3"),
        )
    }

    @GetMapping("/{messageId}")
    fun getMessage(@PathVariable messageId: String): Message {
        return Message(messageId.toLong(), "First Message")
    }

}

data class Message(val id: Long, val text: String)