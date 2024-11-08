package com.vortex.msp.Service;

import com.vortex.msp.Entity.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {

    boolean saveMessage(Message message);

    boolean updateMessage(Message message);

    boolean deleteMessage(Integer messageId);

    Message getMessage(Integer messageId);

    List<Message> listMessages();

    List<Message> listMessagesByUser(Integer userId);

    List<Message> listMessagesByDate(LocalDate date);

    List<Message> limitMessages(Integer pageSize, Integer index);

    Integer countMessageByUser(Integer userId);

    Integer countMessage();
}
