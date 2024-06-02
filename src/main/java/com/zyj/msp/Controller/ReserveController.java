package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Reserve;
import com.zyj.msp.Service.ReserveService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    @Autowired
    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    //新增预约
    @PostMapping
    public Result saveReserve(@RequestBody Reserve reserve) {
        boolean flag = reserveService.saveReserve(reserve);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    //删除预约
    @DeleteMapping("/{id}")
    public Result deleteReserve(@PathVariable("id") Integer id) {
        boolean flag = reserveService.deleteReserve(id);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    //修改预约
    @PutMapping
    public Result updateReserve(@RequestBody Reserve reserve) {
        boolean flag = reserveService.update(reserve);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    //根据id查询预约
    @GetMapping("/{id}")
    public Result getReserve(@PathVariable("id") Integer id) {
        Reserve reserve = reserveService.getReserve(id);
        return Result.SUCCEED(reserve);
    }

    //根据预约人姓名查询预约
    @GetMapping("/{username}")
    public Result listReservesByUsername(@PathVariable("username") String username) {
        List<Reserve> reserves = reserveService.listReservesByUsername(username);
        return Result.SUCCEED(reserves);
    }

    //根据预约人手机号查询预约
    @GetMapping("/{phone}")
    public Result listReservesByPhone(@PathVariable("phone") String phone) {
        List<Reserve> reserves = reserveService.listReservesByPhone(phone);
        return Result.SUCCEED(reserves);
    }

    //查询所有预约
    @GetMapping("/list")
    public Result listAllReserves() {
        List<Reserve> reserves = reserveService.listAllReserves();
        return Result.SUCCEED(reserves);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    public Result listReserves(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Reserve> reserves = reserveService.limitReserves(pageSize, index);
        return Result.SUCCEED(reserves);
    }

}
