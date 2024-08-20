package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Reserve;
import com.zyj.msp.Service.ReserveService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
public class ReserveController implements BaseController<Reserve> {

    private final ReserveService reserveService;

    @Autowired
    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping
    @Override
    public Result save(@RequestBody Reserve reserve) {
        boolean flag = reserveService.saveReserve(reserve);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result save(List<Reserve> reserves) {
        return null;
    }

    @DeleteMapping("/{reserveId}")
    @Override
    public Result delete(@PathVariable("reserveId") Integer reserveId) {
        boolean flag = reserveService.deleteReserve(reserveId);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result delete(List<Integer> ids) {
        return null;
    }

    @PutMapping
    @Override
    public Result update(@RequestBody Reserve reserve) {
        boolean flag = reserveService.update(reserve);
        return flag ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result update(List<Reserve> reserves) {
        return null;
    }

    @GetMapping("/{reserveId}")
    @Override
    public Result get(@PathVariable("reserveId") Integer reserveId) {
        Reserve reserve = reserveService.getReserve(reserveId);
        return Result.SUCCEED(reserve);
    }

    @GetMapping
    @Override
    public Result list() {
        List<Reserve> reserves = reserveService.listAllReserves();
        return Result.SUCCEED(reserves);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    @Override
    public Result limit(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Reserve> reserves = reserveService.limitReserves(pageSize, index);
        return Result.SUCCEED(reserves);
    }

    @Override
    public Result count() {
        return null;
    }

    /**
     * 根据预约人姓名查询预约
     *
     * @param username 预约用户名
     * @return 查询结果
     */
    @GetMapping("/name/{username}")
    public Result listReservesByUsername(@PathVariable("username") String username) {
        List<Reserve> reserves = reserveService.listReservesByUsername(username);
        return Result.SUCCEED(reserves);
    }

    //根据预约人手机号查询预约
    @GetMapping("/phone/{phone}")
    public Result listReservesByPhone(@PathVariable("phone") String phone) {
        List<Reserve> reserves = reserveService.listReservesByPhone(phone);
        return Result.SUCCEED(reserves);
    }
}
