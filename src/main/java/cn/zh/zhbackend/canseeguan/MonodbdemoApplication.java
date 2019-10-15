package cn.zh.zhbackend.canseeguan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*",allowCredentials = "true")
@SpringBootApplication
@ComponentScan(basePackages = "cn.zh.zhbackend.canseeguan.*")
public class MonodbdemoApplication {

//    @Bean
//    public FilterRegistrationBean jwtFilter() {
//        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        JwtAuthenticatiaonFilter filter = new JwtAuthenticationFilter();
//        registrationBean.setFilter(filter);
//        return registrationBean;
//    }
    public static void main(String[] args) {
        SpringApplication.run(MonodbdemoApplication.class, args);
    }

}
