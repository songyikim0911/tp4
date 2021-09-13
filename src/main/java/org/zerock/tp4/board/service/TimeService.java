package org.zerock.tp4.board.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TimeService {

    String getNow();

    void addString(String str);
}
