package fr.af.test.offer.usr.aop;

import fr.af.test.offer.usr.service.LoggerService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.format.PeriodFormat;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@AllArgsConstructor
public class LoggerAspect {

    private final LoggerService loggerService;

    @Pointcut(value = "execution(* fr.af.test.offer.usr.controller.UserController.*(..))")
    private void logCall() {
        // log pointcut
    }

    @Around(value= "logCall()")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        loggerService.log("Input", jp.getArgs()[0]);
        Object result = null;
        Instant start = Instant.now();
        try {
            result = jp.proceed();
        } finally {
            Instant finish = Instant.now();
            org.joda.time.Duration duration = org.joda.time.Duration.millis(Duration.between(start, finish).toMillis());
            loggerService.log("Output", result);
            loggerService.log("Process time", PeriodFormat.getDefault().print(duration.toPeriod()));
            return result;
        }
        
    }
}
