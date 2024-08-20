package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Room;
import com.zyj.msp.Service.RoomService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController implements BaseController<Room> {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @Override
    public Result save(@RequestBody Room room) {
        boolean result = roomService.saveRoom(room);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result save(List<Room> rooms) {
        return null;
    }

    @DeleteMapping("/{roomId}")
    @Override
    public Result delete(@PathVariable("roomId") Integer roomId) {
        boolean result = roomService.deleteRoom(roomId);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/list")
    @Override
    public Result delete(@RequestParam("roomIds") List<Integer> roomIds) {
        int succCount = 0;
        for (Integer roomId : roomIds) {
            boolean isDelete = roomService.deleteRoom(roomId);
            if (isDelete) {
                succCount++;
            } else {
                return Result.FAILED();
            }
        }
        return Result.SUCCEED(succCount);
    }

    @PutMapping
    @Override
    public Result update(@RequestBody Room room) {
        System.out.println(room);
        boolean result = roomService.updateRoom(room);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result update(List<Room> rooms) {
        return null;
    }

    @GetMapping("/{roomId}")
    @Override
    public Result get(Integer roomId) {
        Room room = roomService.getRoom(roomId);
        return Result.SUCCEED(room);
    }

    @GetMapping
    @Override
    public Result list() {
        List<Room> rooms = roomService.listRooms();
        return Result.SUCCEED(rooms);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    @Override
    public Result limit(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Room> rooms = roomService.limitRooms(pageSize, index);
        return Result.SUCCEED(rooms);
    }

    @GetMapping("/count")
    @Override
    public Result count() {
        Integer count = roomService.getRoomCount();
        return Result.SUCCEED(count);
    }

    @GetMapping("/name/{roomName}")
    public Result getRoomsByName(@PathVariable("roomName") String roomName) {
        List<Room> rooms = roomService.getRoomsByName(roomName);
        return Result.SUCCEED(rooms);
    }
}
