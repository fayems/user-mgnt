package fr.af.test.offer.usr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggerService {
    public void log(Object msg) {
        log.info("{}", msg);
    }
}
