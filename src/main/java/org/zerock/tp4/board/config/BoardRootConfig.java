package org.zerock.tp4.board.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MapperScan(basePackages = "org.zerock.tp4.board.mapper")
@ComponentScan(basePackages = "org.zerock.tp4.board.service")
@Import(BoardAOPConfig.class)
public class BoardRootConfig {



}
