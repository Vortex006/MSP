package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper {

    Integer saveRoom(Room room);

    Integer updateRoom(Room room);

    Integer deleteRoom(Integer roomId);

    Room getRoom(Integer roomId);

    List<Room> getRoomsByName(String roomName);

    Integer getRoomCount();

    List<Room> listRooms();

    List<Room> limitRooms(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

}
