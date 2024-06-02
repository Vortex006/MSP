package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Room;
import com.zyj.msp.Service.RoomService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public Result saveRoom(@RequestBody Room room) {
        boolean result = roomService.saveRoom(room);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @PutMapping
    public Result updateRoom(@RequestBody Room room) {
        System.out.println(room);
        boolean result = roomService.updateRoom(room);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/{roomId}")
    public Result deleteRoom(@PathVariable("roomId") Integer roomId) {
        boolean result = roomService.deleteRoom(roomId);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/list")
    public Result deleteRooms(@RequestParam("roomIds") List<Integer> roomIds) {
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

    @GetMapping("/{roomId}")
    public Result getRoom(@PathVariable("roomId") Integer roomId) {
        Room room = roomService.getRoom(roomId);
        return Result.SUCCEED(room);
    }

    @GetMapping("/name/{roomName}")
    public Result getRoomByName(@PathVariable("roomName") String roomName) {
        Room room = roomService.getRoomByName(roomName);
        return Result.SUCCEED(room);
    }

    @GetMapping("/list")
    public Result listRooms() {
        List<Room> rooms = roomService.listRooms();
        return Result.SUCCEED(rooms);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    public Result limitRooms(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Room> rooms = roomService.limitRooms(pageSize, index);
        return Result.SUCCEED(rooms);
    }

    @GetMapping("/count")
    public Result getRoomCount() {
        Integer count = roomService.getRoomCount();
        return Result.SUCCEED(count);
    }

}
