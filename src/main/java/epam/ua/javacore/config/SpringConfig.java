package epam.ua.javacore.config;

import epam.ua.javacore.annotation.TimedPostProcessor;
import org.springframework.context.annotation.*;


@Configuration
@ComponentScan(value = {"epam.ua.javacore.service","epam.ua.javacore.repository"})
@Import({WebConfig.class,SecurityConfig.class,DbConfig.class})

public class SpringConfig {

@Bean
TimedPostProcessor timedPostProcessor(){
    return new TimedPostProcessor();
}


}
