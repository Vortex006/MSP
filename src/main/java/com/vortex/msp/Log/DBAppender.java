package com.vortex.msp.Log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.vortex.msp.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBAppender extends AppenderBase<ILoggingEvent> {

    private final LogService logService;

    @Autowired
    public DBAppender(LogService logService) {
        this.logService = logService;
    }

    @Override
    protected void append(ILoggingEvent event) {
//        if (!event.getLoggerName().startsWith("com.vortex.msp")) return;
//
//        long timestamp = event.getTimeStamp();
//        String time = DateUtil.getLocalDateTime(timestamp).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss" +
//                ".SSS"));
//
//        LogEntity log = new LogEntity(null, time, event.getLevel().toString(), event.getThreadName(),
//                event.getLoggerName(), event.getMessage());
//        logService.saveLog(log);
    }
}
