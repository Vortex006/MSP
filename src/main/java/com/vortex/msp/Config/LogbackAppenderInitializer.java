package com.vortex.msp.Config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.vortex.msp.Log.DBAppender;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LogbackAppenderInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final DBAppender dbAppender;

    @Autowired
    public LogbackAppenderInitializer(DBAppender dbAppender) {
        this.dbAppender = dbAppender;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = lc.getLogger("ROOT");
        dbAppender.start();
        root.addAppender(dbAppender);
    }
}
