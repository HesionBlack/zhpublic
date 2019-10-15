package cn.zh.zhbackend.canseeguan.controller;/**
 * ClassName: HelloController <br/>
 * Description: <br/>
 * date: 2019/10/10 下午5:06<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: zhbackend
 *
 * @description:  测试http拦截器
 *
 * @author: zxb
 *
 * @create: 2019-10-10 17:06
 **/
@RestController
public class HelloController {
   @GetMapping("/hello")
    public String hello(){
       return "hello jwt";
   }

   @PostMapping("/helloadmin")
    public String admin(){
       return "hello admin";
   }
}
