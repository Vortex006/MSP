package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Appointment;
import com.vortex.msp.Entity.REQ.REQ_AddAppointment;
import com.vortex.msp.Enum.AppointmentStates;
import com.vortex.msp.Service.AppointmentService;
import com.vortex.msp.Service.MessageService;
import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Utils.DateUtil;
import com.vortex.msp.Utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final RedisService redisService;
    private final MessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AppointmentController(AppointmentService appointmentService, RedisService redisService,
                                 MessageService messageService) {
        this.appointmentService = appointmentService;
        this.redisService = redisService;
        this.messageService = messageService;
    }

    @PostMapping("/terminal/Addppointment")
    public Result AddAppointmentByTerminal(@RequestBody REQ_AddAppointment request) {
        String title = "%s，挂号成功";
        String ss = "### 尊敬的%s，您已预约挂号成功！\n" +
                "以下是您预约的详细信息：\n" +
                "- 就诊日期：%s\n" +
                "- 就诊时间：%s-%s\n" +
                "- 就诊科室：%s\n" +
                "- 就诊医生：%s\n" +
                "- 科室地址：%s \n" + "  \n" +
                "请按时到达医院，并在就诊前至少15分钟到达以完成登记手续。\n\n" +
                "如果您需要取消或更改预约，请提前联系我们。\n\n" +
                "感谢您选择我们的医疗服务。祝您健康！\n\n" +
                "如有任何疑问，请联系客服热线：0000-123456789\n\n" +
                "智慧医疗服务平台\n\n" + "%s";
        Integer scheduleId = request.getScheduleId();
        Integer patientId = request.getPatientId();
        String lockScheduleKey = "lock:schedule:%s:%s";
        boolean islock = redisService.hasKey(String.format(lockScheduleKey, scheduleId, patientId));
        if (!islock) {
            return Result.FAILED("未查询到锁号记录");
        }
        Appointment appointment = Appointment.builder()
                .scheduleId(scheduleId)
                .patientId(patientId)
                .appointmentState(AppointmentStates.NORMAL.getCode())
                .build();
        Map<String, Integer> map = appointmentService.saveAppointment(appointment);
        boolean isSave = map.getOrDefault("rowCount", 0) > 0;
        if (isSave) {
            Map<String, String> appointmentInfo = appointmentService.getAppointmentInfo(map.getOrDefault(
                    "appointmentId", 0));
            if (appointmentInfo == null) {
                logger.error("查询挂号信息失败");
            } else {
                String mt = String.format(title, appointmentInfo.get("patient_name"));
                String ms = String.format(ss, appointmentInfo.get("patient_name"), appointmentInfo.get("schedule_date"
                ), appointmentInfo.get("schedule_startTime"), appointmentInfo.get("schedule_endTime"),
                        appointmentInfo.get("deptment_name"), appointmentInfo.get("doctor_name"),
                        appointmentInfo.get("deptment_place"), DateUtil.now());
                Boolean isSuccess = messageService.sendDingMsg(mt, ms);
                if (!isSuccess) {
                    logger.error("钉钉通知消息发送失败");
                }
            }
        }
        return isSave ? Result.SUCCEED() : Result.FAILED("挂号失败");
    }

}
