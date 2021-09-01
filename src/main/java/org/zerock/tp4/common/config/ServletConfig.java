package org.zerock.tp4.common.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.zerock.tp4.board.config.BoardServletConfig;
import org.zerock.tp4.common.converter.StringToLocalDateTimeConverter;

@EnableWebMvc
@Import(BoardServletConfig.class)
@ComponentScan(basePackages = "org.zerock.tp4.common.exception")
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
       registry.addConverter(new StringToLocalDateTimeConverter());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        registry.viewResolver(bean);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //이 경로 (webapp resources folder) 는 스프링 영향을 주지 않겠다는 의미.
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

    }
}
