package org.zerock.tp4.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "org.zerock.tp4.security.controller")
public class SecurityServletConfig {

}
