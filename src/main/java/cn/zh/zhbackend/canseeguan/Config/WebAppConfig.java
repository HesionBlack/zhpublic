package cn.zh.zhbackend.canseeguan.Config;/**
 * ClassName: WebAppConfig <br/>
 * Description: <br/>
 * date: 2019/10/11 上午9:30<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.Filter.HttpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: springsecurity
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-11 09:30
 **/
//@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login");
    }
}
