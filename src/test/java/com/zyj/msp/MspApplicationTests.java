package com.zyj.msp;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MspApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MspApplicationTests.class);

    @Test
    void contextLoads() {
        // 创建时间格式化对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = LocalDate.parse("2024-06-24");
        // 设置开始和结束时间
        LocalTime startTime = LocalTime.parse("08:00", timeFormatter);
        LocalTime endTime = LocalTime.parse("18:00", timeFormatter);

        // 特定排除的开始和结束时间
        LocalTime excludeStart = LocalTime.parse("11:30", timeFormatter);
        LocalTime excludeEnd = LocalTime.parse("13:30", timeFormatter);

        // 存储每15分钟的时间段，排除特定时间段
        List<String> intervals = new ArrayList<>();

        // 使用Duration设置每15分钟的间隔
        Duration duration = Duration.ofMinutes(15);

        while (startTime.isBefore(endTime)) {
            StringBuilder sb = new StringBuilder();
            if (startTime.isBefore(excludeStart) || startTime.isAfter(excludeEnd)) {
                sb.append(startTime.format(timeFormatter));
                sb.append("-");
                startTime = startTime.plus(duration);
                sb.append(startTime.format(timeFormatter));
                intervals.add(sb.toString());
            } else {
                startTime = startTime.plus(duration);
            }
        }
        logger.info("{}", intervals);
    }

    @Test
    void jsonTest() throws Exception {
        String path = "D:\\Vortex\\abc.png";
// 画印章
        SealUtils.OfficialSeal_1(path, "巴拉巴拉阿巴阿爸");

    }


}

