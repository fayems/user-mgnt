package fr.af.test.offer.usr.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Test LoggerService service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoggerService.class)
public class LoggerServiceTest {

    @Autowired
    private LoggerService loggerService;

    Logger logger = (Logger) LoggerFactory.getLogger(LoggerService.class);
    ListAppender<ILoggingEvent> listAppender;

    @Before
    public void setup() {
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void logTest() {
        loggerService.log("log title", "log message");
        List<ILoggingEvent> logsList = listAppender.list;
        Assert.assertEquals("log title : log message", logsList.get(0).getFormattedMessage());
        Assert.assertEquals(Level.INFO, logsList.get(0).getLevel());
    }

}
