package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Doctor;
import com.vortex.msp.Entity.REQ.REQ_GetScheduleDoctor;
import com.vortex.msp.Entity.RESP.RESP_GetScheduleDoctor;
import com.vortex.msp.Entity.Schedule;
import com.vortex.msp.Service.DoctorService;
import com.vortex.msp.Service.ScheduleService;
import com.vortex.msp.Utils.DateUtil;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ScheduleService scheduleService;
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(ScheduleService scheduleService, DoctorService doctorService) {
        this.scheduleService = scheduleService;
        this.doctorService = doctorService;
    }


    @Operation(summary = "终端获取排班医生信息")
    @PostMapping("/terminal/GetScheduleDoctor")
    public Result GetScheduleDoctorByTerminal(@RequestBody REQ_GetScheduleDoctor request) {
        List<LocalDate> dates = DateUtil.getDatesBetween(request.getStartDate(), request.getEndDate());
        List<Schedule> schedules = scheduleService.getScheduleByDates(dates);
        if (schedules == null || schedules.size() == 0) {
            return Result.FAILED("未查询到排班医生信息");
        }
        List<Integer> docIds =
                schedules.stream().filter(schedule -> schedule.getScheduleDeptmentId().equals(request.getDepartmentId()))
                .map(Schedule::getScheduleDoctorId).distinct().collect(Collectors.toList());

        List<Doctor> doctors = doctorService.getDoctors(docIds);
        List<RESP_GetScheduleDoctor> resp_getScheduleDoctors = doctors.stream().map(doctor -> {
            RESP_GetScheduleDoctor resp = new RESP_GetScheduleDoctor();
            resp.setDoctorId(doctor.getDoctorId());
            resp.setDoctorName(doctor.getDoctorName());
            resp.setDoctorTitle(doctor.getDoctorTitle());
            resp.setRegFee(new BigDecimal("9.6"));
            return resp;
        }).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("result", resp_getScheduleDoctors);
        result.put("time", DateUtil.getLocalDateTimeStr(request.getTime()));
        result.put("traceId", request.getTraceId());
        return Result.SUCCEED(result);
    }


}
