package cn.security.interceptors;/**
 * ClassName: HttpInterceptor <br/>
 * Description: <br/>
 * date: 2019/10/11 上午9:28<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: springsecurity
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-11 09:28
 **/
@ControllerAdvice
public class HttpInterceptor implements HandlerInterceptor {
    /**
     * 请求执行之前进行调用,对请求进行预处理，
     * 返回true之后可继续往下执行调用下一个Interceptor。或者Controller
     * ,返回false终止请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求前拦截");
        return true;
    }

    /**
     * 执行完Controller之后 && DispatcherServlet进行视图渲染之前执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("请求完成后的操作...");
    }

    /**
     * DispatcherServlet渲染之后取执行。可以用于资源清理等工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("视图渲染之后...");
    }
}
