package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Entity.Doctor;
import com.vortex.msp.Entity.REQ.REQ_GetScheduleInfo;
import com.vortex.msp.Entity.REQ.REQ_LockSchedule;
import com.vortex.msp.Entity.REQ.REQ_UnLockSchedule;
import com.vortex.msp.Entity.RESP.RESP_GetScheduleInfo;
import com.vortex.msp.Entity.Schedule;
import com.vortex.msp.Entity.VO.ScheduleVo;
import com.vortex.msp.Enum.AppointmentStates;
import com.vortex.msp.Service.*;
import com.vortex.msp.Utils.DateUtil;
import com.vortex.msp.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController implements BaseController<Schedule> {

    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;
    private final RedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DeptmentService deptmentService;
    private final DoctorService doctorService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, AppointmentService appointmentService,
                              RedisService redisService, DeptmentService deptmentService, DoctorService doctorService) {
        this.scheduleService = scheduleService;
        this.appointmentService = appointmentService;
        this.redisService = redisService;
        this.deptmentService = deptmentService;
        this.doctorService = doctorService;
    }

    @Override
    public Result save(Schedule schedule) {
        return null;
    }

    @Override
    public Result save(List<Schedule> schedules) {
        return null;
    }

    @Override
    public Result delete(Integer id) {
        return null;
    }

    @Override
    public Result delete(List<Integer> ids) {
        return null;
    }

    @Override
    public Result update(Schedule schedule) {
        return null;
    }

    @Override
    public Result update(List<Schedule> schedules) {
        return null;
    }

    @Override
    public Result get(Integer id) {
        return null;
    }

    @Override
    public Result list() {
        List<Schedule> schedules = scheduleService.listSchedules();
        return Result.SUCCEED(schedules);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    @Override
    public Result limit(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Schedule> schedules = scheduleService.limitSchedules(pageSize, index);
        if (schedules == null) {
            return Result.FAILED("未查询到分时段号源信息");
        } else {
            List<ScheduleVo> scheduleListVo = schedules.stream()
                    .map(scheduleService::DoToVo)
                    .collect(Collectors.toList());
            return Result.SUCCEED(scheduleListVo);
        }
    }

    @GetMapping("/count")
    @Override
    public Result count() {
        Integer count = scheduleService.getScheduleCount();
        return Result.SUCCEED(count);
    }

    @PostMapping("/terminal/GetScheduleInfo")
    public Result GetScheduleByTerminal(@RequestBody REQ_GetScheduleInfo request) {
        List<Schedule> scheduleTemp = scheduleService.getScheduleByDate(request.getDate());
        List<Schedule> schedules =
                scheduleTemp.stream().filter(schedule -> schedule.getScheduleDeptmentId().equals(request.getDeptmentId()) && schedule.getScheduleDoctorId().equals(request.getDoctorId())).collect(Collectors.toList());
        if (schedules.size() == 0) {
            return Result.FAILED("未查询到分时段号源信息");
        }
        List<RESP_GetScheduleInfo> resp_getScheduleInfos =
                schedules.stream().map(schedule -> RESP_GetScheduleInfo.builder()
                        .scheduleId(schedule.getScheduleId())
                        .deptmentId(schedule.getScheduleDeptmentId())
                        .doctorId(schedule.getScheduleDoctorId())
                        .scheduleDate(schedule.getScheduleDate())
                        .startTime(schedule.getScheduleStartTime())
                        .endTime(schedule.getScheduleEndTime())
                        .scheduleType(schedule.getScheduleType())
                        .scheduleNum(schedule.getScheduleNum())
                        .build()).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("result", resp_getScheduleInfos);
        result.put("time", DateUtil.getLocalDateTimeStr(request.getTime()));
        result.put("traceId", request.getTraceId());
        return Result.SUCCEED(result);
    }

    @PostMapping("/terminal/LockSchedule")
    public Result LockScheduleByTerminal(@RequestBody REQ_LockSchedule request) {
        Integer scheduleId = request.getScheduleId();
        Integer patientId = request.getPatientId();
        String lockScheduleKey = "lock:schedule:%s:%s";

        // 判断该排班是否存在
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        if (schedule == null) {
            return Result.FAILED("该排班不存在");
        }

        //判断该排班是否已经被该用户锁定
        boolean islock = redisService.hasKey(String.format(lockScheduleKey, scheduleId, patientId));
        if (islock) {
            return Result.FAILED("该排班已锁定");
        }

        // 判断是否可以锁这个排班
        LocalDateTime scheDateTime = schedule.getScheduleDate().atTime(schedule.getScheduleEndTime());
        if (LocalDateTime.now().isAfter(scheDateTime)) {
            return Result.FAILED("该排班已过期");
        }

        //获取该排班的号源数量
        Integer totalNum = schedule.getScheduleNum();

        //去数据库中查这个排班ID的有效挂号记录数量为A
        int useNum =
                appointmentService.getAppointmentBySchedule(request.getScheduleId(), AppointmentStates.NORMAL).size();
        if (useNum >= totalNum) {
            return Result.FAILED("该排班已满");
        }

        //获取该排班已经被锁定的数量为B
        int lockNum = redisService.keys(String.format(lockScheduleKey, request.getPatientId(), "*")).size();

        //如果A+B 超过该排班的最大号源数量，则锁定失败
        if (useNum + lockNum >= totalNum) {
            return Result.FAILED("该排班已满");
        }

        //锁定排班
        boolean lock = redisService.setString(String.format(lockScheduleKey, scheduleId, patientId), "1", 10,
                TimeUnit.MINUTES);
        if (!lock) {
            return Result.FAILED("锁定失败");
        }
        return Result.SUCCEED();
    }

    @PostMapping("/terminal/UnLockSchedule")
    public Result UnLockScheduleByTerminal(@RequestBody REQ_UnLockSchedule request) {
        Integer scheduleId = request.getScheduleId();
        Integer patientId = request.getPatientId();
        String lockScheduleKey = "lock:schedule:%s:%s";

        // 判断该排班是否存在
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        if (schedule == null) {
            return Result.FAILED("该排班不存在");
        }

        //判断该排班是否已经被该用户锁定
        boolean islock = redisService.hasKey(String.format(lockScheduleKey, scheduleId, patientId));
        if (islock) {
            Boolean isDel = redisService.delete(String.format(lockScheduleKey, scheduleId, patientId));
            if (!isDel) {
                logger.error("解锁号源失败,患者ID==>" + patientId + "排班ID==>" + scheduleId);
                return Result.FAILED("解锁失败");
            }
        }
        return Result.SUCCEED();
    }

    @PostMapping("/test")
    public Result test(@RequestBody Map<String, String> map) {
        List<Schedule> tempSchedules;
        String date = map.getOrDefault("date", "");
        String time = map.getOrDefault("time", "");
        String dept = map.getOrDefault("dept", "");
        String doctor = map.getOrDefault("doctor", "");
        String title = map.getOrDefault("title", "");

        if (!StringUtils.hasText(date)) {
            return Result.FAILED("日期不能为空");
        }

        if (!StringUtils.hasText(dept)) {
            return Result.FAILED("科室不能为空");
        }

        List<Schedule> scheduleByDate = scheduleService.getScheduleByDate(LocalDate.parse(date));

        if (scheduleByDate.size() == 0) {
            return Result.FAILED("该日期没有排班");
        }

        List<Deptment> deptmentsByName = deptmentService.getDeptmentsByName(dept);

        if (deptmentsByName.size() == 0) {
            return Result.FAILED("科室不存在");
        }

        Set<Integer> deptmentIdSet = deptmentsByName.stream()
                .map(Deptment::getDeptmentId)
                .collect(Collectors.toSet());

        tempSchedules = scheduleByDate.stream()
                .filter(sche -> deptmentIdSet.contains(sche.getScheduleDeptmentId()))
                .collect(Collectors.toList());

        if (StringUtils.hasText(doctor)) {
            List<Doctor> doctorByName = doctorService.getDoctorByName(doctor);
            Set<Integer> doctorIdSet = doctorByName.stream()
                    .map(Doctor::getDoctorId)
                    .collect(Collectors.toSet());
            tempSchedules =
                    tempSchedules.stream().filter(schedule -> doctorIdSet.contains(schedule.getScheduleDoctorId())).collect(Collectors.toList());
        }

        if (StringUtils.hasText(time)) {
            LocalTime localTime = DateUtil.getLocalTime(time);
            tempSchedules =
                    tempSchedules.stream().filter(schedule -> schedule.getScheduleStartTime().isAfter(localTime)).collect(Collectors.toList());
        }

        if (tempSchedules.size() == 0) {
            return Result.FAILED("该科室没有该时间段的排班");
        }

        return Result.SUCCEED(tempSchedules);
    }

}
