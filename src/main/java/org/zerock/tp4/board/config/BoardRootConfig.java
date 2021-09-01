package org.zerock.tp4.board.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.zerock.tp4.board.mapper")
@ComponentScan(basePackages = "org.zerock.tp4.board.service")
public class BoardRootConfig {

}
