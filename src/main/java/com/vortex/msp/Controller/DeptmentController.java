package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Service.DeptmentService;
import com.vortex.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deptment")
public class DeptmentController implements BaseController<Deptment> {

    private final DeptmentService deptmentService;

    @Autowired
    public DeptmentController(DeptmentService deptmentService) {
        this.deptmentService = deptmentService;
    }

    @PostMapping
    @Override
    public Result save(@RequestBody Deptment deptment) {
        boolean result = deptmentService.saveDeptment(deptment);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result save(List<Deptment> deptments) {
        return null;
    }

    @DeleteMapping("/{deptmentId}")
    @Override
    public Result delete(@PathVariable("deptmentId") Integer deptmentId) {
        boolean result = deptmentService.deleteDeptment(deptmentId);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/list")
    @Override
    public Result delete(@RequestParam("deptmentIds") List<Integer> deptmentIds) {
        int succCount = 0;
        for (Integer deptmentId : deptmentIds) {
            boolean isDelete = deptmentService.deleteDeptment(deptmentId);
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
    public Result update(@RequestBody Deptment deptment) {
        System.out.println(deptment);
        boolean result = deptmentService.updateDeptment(deptment);
        return result ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result update(List<Deptment> deptments) {
        return null;
    }

    @GetMapping("/{deptmentId}")
    @Override
    public Result get(Integer deptmentId) {
        Deptment deptment = deptmentService.getDeptment(deptmentId);
        return Result.SUCCEED(deptment);
    }

    @GetMapping
    @Override
    public Result list() {
        List<Deptment> deptments = deptmentService.listDeptments();
        return Result.SUCCEED(deptments);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    @Override
    public Result limit(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Deptment> deptments = deptmentService.limitDeptments(pageSize, index);
        return Result.SUCCEED(deptments);
    }

    @GetMapping("/count")
    @Override
    public Result count() {
        Integer count = deptmentService.getDeptmentCount();
        return Result.SUCCEED(count);
    }

    @GetMapping("/name/{deptmentName}")
    public Result getDeptmentsByName(@PathVariable("deptmentName") String deptmentName) {
        List<Deptment> deptments = deptmentService.getDeptmentsByName(deptmentName);
        return Result.SUCCEED(deptments);
    }

    @PostMapping("/terminal/getDeptment")
    public Result getDeptmentsByZZJ(@RequestBody Deptment deptment) {
        Integer deptType = deptment.getDeptmentType();
        Integer deptParent = deptment.getDeptmentParent();
        if (ObjectUtils.isEmpty(deptType) || ObjectUtils.isEmpty(deptParent)) {
            throw new ParameterNullException("参数不能为空");
        }
        List<Deptment> deptments = deptmentService.getDeptmentsByZZJ(deptType, deptParent);
        return Result.SUCCEED(deptments);
    }
}
