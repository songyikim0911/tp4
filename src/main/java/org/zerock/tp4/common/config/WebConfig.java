package org.zerock.tp4.common.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.zerock.tp4.board.config.BoardRootConfig;
import org.zerock.tp4.board.config.BoardServletConfig;
import org.zerock.tp4.security.config.SecurityConfig;
import org.zerock.tp4.security.config.SecurityServletConfig;

import javax.servlet.*;

@Log4j2
    public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {




        @Override
        protected Class<?>[] getRootConfigClasses() {
            log.info("1------------");
            log.info("1------------");
            return new Class[]{RootConfig.class, SecurityConfig.class};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            log.info("2------------");
            log.info("2------------");
            return new Class[]{ServletConfig.class, SecurityServletConfig.class};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }

    @Override
    protected Filter[] getServletFilters() {
       //post방식만 해당, get방식은 한글 처리가 잘 안될수도있다.
        CharacterEncodingFilter utf8Filter = new CharacterEncodingFilter();
        utf8Filter.setEncoding("UTF-8");
        utf8Filter.setForceEncoding(true);

        return new Filter[]{utf8Filter};
        }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound","true");

        MultipartConfigElement multipartConfigElement
                = new MultipartConfigElement("C:\\upload\\temp", 1024*1024*10, 1024*1024*20, 1024*1024*1);
        //10MB -최대 20MB

        registration.setMultipartConfig((multipartConfigElement));

    }
}
