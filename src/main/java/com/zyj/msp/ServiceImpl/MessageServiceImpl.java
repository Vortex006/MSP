package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.Message;
import com.zyj.msp.Exception.ParameterNullException;
import com.zyj.msp.Mapper.MessageMapper;
import com.zyj.msp.Service.MessageService;
import com.zyj.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public boolean saveMessage(Message message) {
        Integer i = messageMapper.saveMessage(message);
        return i > 0;
    }

    @Override
    public boolean updateMessage(Message message) {
        Integer i = messageMapper.updateMessage(message);
        return i > 0;
    }

    @Override
    public boolean deleteMessage(Integer messageId) {
        Integer i = messageMapper.deleteMessage(messageId);
        return i > 0;
    }

    @Override
    public Message getMessage(Integer messageId) {
        Message message = messageMapper.getMessage(messageId);
        return message;
    }

    @Override
    public List<Message> listMessages() {
        List<Message> messages = messageMapper.listMessages();
        return messages;
    }

    @Override
    public List<Message> listMessagesByUser(Integer userId) {
        List<Message> messages = messageMapper.listMessagesByUser(userId);
        return messages;
    }

    @Override
    public List<Message> listMessagesByDate(LocalDate date) {
        List<Message> messages = messageMapper.listMessagesByDate(date);
        return messages;
    }

    @Override
    public List<Message> limitMessages(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Message> messages = messageMapper.limitMessages(pageSize, offset);
        return messages;
    }

    @Override
    public Integer countMessageByUser(Integer userId) {
        Integer count = messageMapper.countMessageByUser(userId);
        return count;
    }

    @Override
    public Integer countMessage() {
        Integer count = messageMapper.countMessage();
        return count;
    }
}


