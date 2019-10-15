package cn.zh.zhbackend.canseeguan.controller;


import cn.zh.zhbackend.canseeguan.Utils.AjaxResponse;
import cn.zh.zhbackend.canseeguan.Utils.JwtUtil;
import cn.zh.zhbackend.canseeguan.domain.ResModel;
import cn.zh.zhbackend.canseeguan.domain.User;
import cn.zh.zhbackend.canseeguan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class userController {

    @Autowired
    private IUserService userService;
    Map<String,Object> map = new HashMap<>();
    @PostMapping("/user/login")
    public Map<String,Object> login(HttpServletResponse response, @RequestBody User user) throws Exception {
        System.out.println(user);
        ResModel login = userService.login(user);

        if (login.getCode()==200){
            String token = JwtUtil.generateToken(user.getUserName());
            response.addHeader("Set-Authorization-Token",token);
            response.addHeader("Access-Control-Expose-Headers","Set-Authorization-Token");
            map.put("data",user);
            map.put("isSuccess",true);
            return map;
//            return AjaxResponse.success(user);
        }
        map.put("isSuccess",false);
        return map;
    }
    @PostMapping("/zh/findall")
    public List<User> findAll() {
        return userService.findAll();
    }


    @PostMapping("/user/add")
    public AjaxResponse Add(@RequestBody User user) throws Exception {
        System.out.println(user);
        ResModel login = userService.add(user);
        if (login.getCode()==200){
            return AjaxResponse.success(user);

        }
        return AjaxResponse.fail();
    }
}
