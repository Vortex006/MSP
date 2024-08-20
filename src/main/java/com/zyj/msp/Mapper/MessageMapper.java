package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MessageMapper {

    Integer saveMessage(Message message);

    Integer updateMessage(Message message);

    Integer deleteMessage(Integer messageId);

    Message getMessage(Integer messageId);

    List<Message> listMessages();

    List<Message> limitMessages(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    List<Message> listMessagesByUser(Integer userId);

    List<Message> listMessagesByDate(LocalDate date);

    Integer countMessageByUser(Integer userId);

    Integer countMessage();
}
