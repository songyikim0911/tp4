package org.zerock.tp4.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.stereotype.Service;
import org.zerock.tp4.board.mapper.TimeMapper;

@Log4j2
@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

    private final TimeMapper timeMapper;

    @Override
    public String getNow() {
        log.info("service...getNow()");
        return timeMapper.getTime2();
    }
}
