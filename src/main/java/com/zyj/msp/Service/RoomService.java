package com.zyj.msp.Service;

import com.zyj.msp.Entity.Room;

import java.util.List;

public interface RoomService {

    boolean saveRoom(Room room);

    boolean updateRoom(Room room);

    boolean deleteRoom(Integer roomId);

    Room getRoom(Integer roomId);

    List<Room> getRoomsByName(String roomName);

    Integer getRoomCount();

    List<Room> listRooms();

    List<Room> limitRooms(Integer pageSize, Integer index);

}
