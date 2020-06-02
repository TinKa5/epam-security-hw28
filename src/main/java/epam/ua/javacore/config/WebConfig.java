package epam.ua.javacore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("epam.ua.javacore.controller")
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/documentation/**").addResourceLocations("/documentation/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/view/**").addResourceLocations("/view/");
    }

    @Bean
    public ViewResolver viewResolver() {
       ViewResolver vr=new InternalResourceViewResolver();
       ((InternalResourceViewResolver) vr).setPrefix("/view/");
       ((InternalResourceViewResolver) vr).setSuffix(".jsp");
        return vr;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/admin-page").setViewName("admin-page");
    }

}
