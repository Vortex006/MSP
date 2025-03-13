package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Entity.REQ.REQ_GetDeptmentInfo;
import com.vortex.msp.Entity.RESP.RESP_GetDeptmentInfo;
import com.vortex.msp.Service.DeptmentService;
import com.vortex.msp.Utils.DateUtil;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deptment")
@Tag(name = "科室模块")
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

    @PostMapping("/terminal/getDeptmentInfo")
    public Result getDeptmentInfoByTerminal(@RequestBody REQ_GetDeptmentInfo request) {
        List<RESP_GetDeptmentInfo> resp_getDeptmentInfos = new ArrayList<>();
        if (StringUtils.hasText(request.getDeptmentNo())) {
            Deptment deptment = deptmentService.getDeptment(Integer.parseInt(request.getDeptmentNo()));
            if (!ObjectUtils.isEmpty(deptment)) {
                RESP_GetDeptmentInfo info = new RESP_GetDeptmentInfo();
                info.setDeptmentNo(deptment.getDeptmentId().toString());
                info.setDeptmentName(deptment.getDeptmentName());
                info.setDeptmentParent(deptment.getDeptmentParent().toString());
                info.setDeptmentPlace(deptment.getDeptmentPlace());
                info.setTime(request.getTime());
                info.setTraceId(request.getTraceId());
                resp_getDeptmentInfos.add(info);
            } else {
                return Result.FAILED("查询科室信息失败");
            }
        } else {
            List<Deptment> deptments = deptmentService.listDeptments();
            if (ObjectUtils.isEmpty(deptments)) {
                return Result.FAILED("查询科室信息失败");
            }
            resp_getDeptmentInfos = deptments.stream().map(deptment -> {
                RESP_GetDeptmentInfo info = new RESP_GetDeptmentInfo();
                info.setDeptmentNo(deptment.getDeptmentId().toString());
                info.setDeptmentName(deptment.getDeptmentName());
                info.setDeptmentParent(deptment.getDeptmentParent().toString());
                info.setDeptmentPlace(deptment.getDeptmentPlace());
                return info;
            }).collect(Collectors.toList());
            Map<String, Object> result = new HashMap<>();
            result.put("result", resp_getDeptmentInfos);
            result.put("time", DateUtil.getLocalDateTimeStr(request.getTime()));
            result.put("traceId", request.getTraceId());
            return Result.SUCCEED(result);
        }
        return Result.SUCCEED(resp_getDeptmentInfos);
    }
}
