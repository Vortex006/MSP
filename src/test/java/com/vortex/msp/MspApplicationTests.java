package com.vortex.msp;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.RandomUtil;
import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Entity.Doctor;
import com.vortex.msp.Entity.Schedule;
import com.vortex.msp.Mapper.AppointmentMapper;
import com.vortex.msp.Service.*;
import com.vortex.msp.Utils.DateUtil;
import com.vortex.msp.Utils.SealUtil;
import com.vortex.msp.Utils.TokenUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class MspApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MspApplicationTests.class);
    @Autowired
    LogService logService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    private RabbitTemplate rabbitService;
    @Autowired
    ScheduleService scheduleService;

    public static String CreateHtml(String fileName, Map<String, Object> dataModel) {
        String htmlContent = "";
        // 初始化Freemarker配置
        Configuration cfg = new Configuration();
        try {
            // 设置模板加载目录
            cfg.setDirectoryForTemplateLoading(new File("src/test/resources/template"));
            // 获取模板
            Template template = cfg.getTemplate(fileName);
            // 使用StringWriter来捕获输出
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            // 获取渲染后的字符串
            htmlContent = stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }

    @Test
    void demo() {
        String queueName = "work.queue";
        for (int i = 0; i < 50; i++) {
            rabbitService.convertAndSend(queueName, "hello worker ,message_" + i);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void date() {
        try {
            SealUtil.CreateSeal("D:\\seal\\seal.png", "检验报告单");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void csv() {
        final CsvReader reader = CsvUtil.getReader();
//假设csv文件在classpath目录下
        final List<Deptment> result = reader.read(
                ResourceUtil.getUtf8Reader("dept.csv"), Deptment.class);
        for (Deptment e : result) {
            deptmentService.saveDeptment(e);
        }
//        result.forEach(e -> logger.info(e.toString()));
    }

    @Test
    void ftl() {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("title", "Welcome Page");
        dataModel.put("name", "World");
        String htmlContent = CreateHtml("demo.ftl", dataModel);
        HtmlGenerator.html2Pdf(htmlContent, "D:\\aabbc.pdf");
    }

    @Autowired
    DoctorService doctorService;
    @Autowired
    RedisService redisService;
    @Autowired
    private EMICardService emicardService;
    @Autowired
    private RedisTemplate<String, Object> redis;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private UssService ussService;

    @Test
    void name() {
        for (int i = 0; i < 100; i++) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String formattedDateTime = now.format(formatter);
            logger.info(formattedDateTime);
        }

    }

    @Test
    void filePath() {
        String str = new ApplicationHome(this.getClass()).getDir().getAbsolutePath();
        logger.info(str);

    }

    @Test
    void token() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJpc3MiOiJWb3J0ZXgiLCJleHAiOjE3MzM4NzAxODEsInVzZXJJZCI6IjEifQ" +
                ".kn1EX19Oe8zk4_f18H8QZrAgnhEfWo0lQSuMzIpyiLg";
        TokenUtil.decodeToken(token);
    }

    @Test
    void test2() {

        List<Doctor> doctors = doctorService.listDoctors();

        List<Deptment> departments = deptmentService.listDeptments();

        Set<Integer> parentOfSecondLevel = departments.stream()
                .filter(dept -> dept.getDeptmentParent() != 0)
                .map(Deptment::getDeptmentId)
                .collect(Collectors.toSet());

        List<Deptment> resultFirstLevelDepts = departments.stream()
                .filter(dept -> dept.getDeptmentParent() == 0 && !parentOfSecondLevel.contains(dept.getDeptmentId()))
                .collect(Collectors.toList());

        List<Deptment> secondLevelDepts = departments.stream()
                .filter(dept -> dept.getDeptmentParent() != 0)
                .collect(Collectors.toList());

        List<Deptment> finalResult = new ArrayList<>();
        finalResult.addAll(resultFirstLevelDepts);
        finalResult.addAll(secondLevelDepts);
        List<Integer> deptIds = finalResult.stream().map(Deptment::getDeptmentId).collect(Collectors.toList());
        LocalDate date = LocalDate.now();

        List<Schedule> schedules = new ArrayList<>();
        //先随机一个科室
        for (int i = 0; i < 100; i++) {
            Integer deptId = RandomUtil.randomEle(deptIds);
//            List<Doctor> docByDept = new ArrayList<>();
//            for (Doctor doc : doctors) {
//                if (doc.getDoctorDeptmentId().equals(deptId)) {
//                    docByDept.add(doc);
//                }
//
//            }
            List<Doctor> docByDept =
                    doctors.stream().filter(doctor -> doctor.getDoctorDeptmentId().equals(deptId)).collect(Collectors.toList());
            Doctor doctor = RandomUtil.randomEle(docByDept);
            if (schedules.stream().anyMatch(schedule -> schedule.getScheduleDate().equals(date) && schedule.getScheduleDoctorId().equals(doctor.getDoctorId()))) {
                i--;
                continue;
            }
            int type = RandomUtil.randomInt(1, 3);
            List<String> scheTime;
            switch (type) {
                case 1:
                    scheTime = DateUtil.getScheTime("07:00", "12:00");
                    break;
                case 2:
                    scheTime = DateUtil.getScheTime("13:00", "18:00");
                    break;
                case 3:
                    scheTime = DateUtil.getScheTime("07:00", "18:00");
                    break;
                default:
                    scheTime = DateUtil.getScheTime("17:00", "24:00");
                    break;
            }
            for (String time : scheTime) {
                Schedule schedule = new Schedule();
                schedule.setScheduleDeptmentId(deptId);
                schedule.setScheduleDoctorId(doctor.getDoctorId());
                schedule.setScheduleDate(date);
                String[] split = time.split("-");
                schedule.setScheduleStartTime(LocalTime.parse(split[0], DateTimeFormatter.ofPattern("HH:mm")));
                schedule.setScheduleEndTime(LocalTime.parse(split[1], DateTimeFormatter.ofPattern("HH:mm")));
                schedule.setScheduleType(type);
                schedule.setScheduleNum(6);
                schedules.add(schedule);
            }
        }
        schedules.forEach(scheduleService::saveSchedule);
    }

    @Test
    void test3() {
        Set<String> sss = redis.keys("sche1:*");
        assert sss != null;
        logger.info(sss.toString());
    }

    @Test
    void test4() {
        Map<String, String> appointmentInfo = appointmentMapper.getAppointmentInfo(0);
        logger.info(appointmentInfo.toString());
    }


}

