package cn.kqk.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auhtor kqk
 * @date 2020/4/16 0016 - 11:09
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //注意前面要加file,不然是访问不了的
        registry.addResourceHandler("/img/**").addResourceLocations("file:F:\\IDEAProject\\static\\img\\");
    }

}
