package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.Room;
import com.zyj.msp.Exception.ParameterNullException;
import com.zyj.msp.Mapper.RoomMapper;
import com.zyj.msp.Service.RoomService;
import com.zyj.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;

    @Autowired
    public RoomServiceImpl(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @Override
    public boolean saveRoom(Room room) {
        int i = roomMapper.saveRoom(room);
        return i > 0;
    }

    @Override
    public boolean updateRoom(Room room) {
        int i = roomMapper.updateRoom(room);
        return i > 0;
    }

    @Override
    public boolean deleteRoom(Integer roomId) {
        int i = roomMapper.deleteRoom(roomId);
        return i > 0;
    }

    @Override
    public Room getRoom(Integer roomId) {
        Room room = roomMapper.getRoom(roomId);
        return room;
    }

    @Override
    public Room getRoomByName(String roomName) {
        Room room = roomMapper.getRoomByName(roomName);
        return room;
    }

    @Override
    public Integer getRoomCount() {
        Integer count = roomMapper.getRoomCount();
        return count;
    }

    @Override
    public List<Room> listRooms() {
        List<Room> rooms = roomMapper.listRooms();
        return rooms;
    }

    @Override
    public List<Room> limitRooms(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Room> rooms = roomMapper.limitRooms(pageSize, offset);
        return rooms;
    }
}
