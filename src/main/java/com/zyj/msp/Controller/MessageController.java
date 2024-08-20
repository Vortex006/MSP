package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Message;
import com.zyj.msp.Service.MessageService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Result saveMessage(@RequestBody Message message) {
        boolean isSave = messageService.saveMessage(message);
        return isSave ? Result.SUCCEED() : Result.FAILED();
    }

    @PutMapping
    public Result updateMessage(@RequestBody Message message) {
        boolean isUpdate = messageService.updateMessage(message);
        return isUpdate ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/{messageId}")
    public Result deleteMessage(@PathVariable("messageId") Integer messageId) {
        boolean isDelete = messageService.deleteMessage(messageId);
        return isDelete ? Result.SUCCEED() : Result.FAILED();
    }

    @GetMapping("/{messageId}")
    public Result getMessage(@PathVariable("messageId") Integer messageId) {
        Message message = messageService.getMessage(messageId);
        return Result.SUCCEED(message);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    public Result limitMessages(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Message> messages = messageService.limitMessages(pageSize, index);
        return Result.SUCCEED(messages);
    }

    @GetMapping("/count")
    public Result countMessage() {
        Integer count = messageService.countMessage();
        return Result.SUCCEED(count);
    }

    @GetMapping("/list")
    public Result listMessages() {
        List<Message> messages = messageService.listMessages();
        return Result.SUCCEED(messages);
    }

    @GetMapping("/user/{userId}")
    public Result listMessagesByUser(@PathVariable("userId") Integer userId) {
        List<Message> messages = messageService.listMessagesByUser(userId);
        return Result.SUCCEED(messages);
    }

    @GetMapping("/date/{date}")
    public Result listMessagesByDate(@PathVariable("date") String date) {
        List<Message> messages = messageService.listMessagesByDate(LocalDate.parse(date));
        return Result.SUCCEED(messages);
    }

    @GetMapping("/count/{userId}")
    public Result countMessageByUser(@PathVariable("userId") Integer userId) {
        Integer count = messageService.countMessageByUser(userId);
        return Result.SUCCEED(count);
    }

}