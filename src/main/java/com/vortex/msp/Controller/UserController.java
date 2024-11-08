package com.vortex.msp.Controller;

import com.vortex.msp.Service.UserService;
import com.vortex.msp.Utils.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/list")
    public Result deleteListUsers(@RequestBody List<Integer> userIds) {
        int succCount = 0;
        for (Integer userId : userIds) {
            boolean isDelete = userService.deleteUser(userId);
            if (isDelete) {
                succCount++;
            } else {
                return Result.FAILED();
            }
        }
        return Result.SUCCEED(succCount);
    }


}
