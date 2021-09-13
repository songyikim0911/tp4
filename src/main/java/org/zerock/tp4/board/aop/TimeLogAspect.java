package org.zerock.tp4.board.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component//스프링 빈을 만들기위해 컴포넌트 설정, ->또 필요한것 이 패키지를 스프링 빈으로 추가해줘야함.->aopconfig에서 componenetscan
public class TimeLogAspect {

    {//객체가 생성되자마자 실행 할 수 있는 default 블록
        //로그를 찍을때 자주 쓰임
        log.info("TimeLogAspect...");
        log.info("TimeLogAspect...");
        log.info("TimeLogAspect...");

    }

    @Before("execution(* org.zerock.tp4.board.service.*.*(..))")//이 기능을 누구랑 합칠지정함 pointcut.
    // * 의미 접근제한자,  *(클래스).*(메서드) 모든 클래스의 모든 메서드
    public void logBefore(JoinPoint joinPoint){
        log.info("logBefore....");

        log.info(joinPoint.getTarget());//실제 객체 - 실제 객체
        log.info(Arrays.toString(joinPoint.getArgs()));//파라미터 값을 찍음

        log.info("logBefore.....END");
    }

    @AfterReturning("execution(* org.zerock.tp4.board.service.*.*(..))")//이 기능을 누구랑 합칠지정함 pointcut.
    public void logAfter(){
        log.info("logAfter....");
    }

}
