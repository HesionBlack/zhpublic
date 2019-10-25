package cn.zh.zhbackend.canseeguan.controller;


import cn.zh.zhbackend.canseeguan.Utils.AESUtil;
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
    Map<String, Object> map = new HashMap<>();

    //登录
    @PostMapping("/user/login")
    public Map<String, Object> login(HttpServletResponse response, @RequestBody User user) throws Exception {
        System.out.println(user);
        ResModel login = userService.login(user);

        if (login.getCode() == 200) {
            String token = JwtUtil.generateToken(user.getUserName());
            response.addHeader("Set-Authorization-Token", token);
            response.addHeader("Access-Control-Expose-Headers", "Set-Authorization-Token");
            map.put("data", user);
            map.put("isSuccess", true);
            return map;
//            return AjaxResponse.success(user);
        }
        map.put("isSuccess", false);
        return map;
    }
    //查询所有用户
    @PostMapping("/zh/findall")
    public List<User> findAll() {
        return userService.findAll();
    }



    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/addUser")
    public AjaxResponse addUser(@RequestBody User user ,HttpServletResponse response) throws Exception {
        String token = userService.getToken(user.getUserId());
        response.setHeader("authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "authorization");
        User user1 = new User();
        user1.setUserId(user.getUserId());

        String pwd = AESUtil.aesEncrypt(user.getUserPw(), "zhonghuan13xxxxx");
        user1.setUserPw(pwd);

        if (userService.findByUserId(user.getUserId()) != null) {
            System.out.println("用户名重复,请重新输入用户名！！");

            return AjaxResponse.fail();
        } else {
            userService.addUser(user1);
        }

        return AjaxResponse.success(user1);
    }

    /**
     * 修改密码
     * @param user
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/changePassword", produces = "application/json")
    public Map<String, Object> updateUser(@RequestBody User user, HttpServletResponse response) throws Exception {
        String newPwd = AESUtil.aesEncrypt(user.getUserPw(),"zhonghuan13xxxxx");
        //判断当前用户输入的旧密码是否与数据库中该用户的密码一致
        if (AESUtil.aesEncrypt(user.getUserPw(), "zhonghuan13xxxxx").equals(userService.findByUserId(user.getUserId()).getUserPw())) {
            user.setUserPwNew(newPwd);
            System.out.println("new:" + user);
            userService.updateUser(user);
            map.put("isSuccess", true);
            map.put("message", "修改成功");
            map.put("code", 200);
            map.put("data", user);
        }
        map.put("isSuccess", false);
        map.put("message", "旧密码不正确，修改失败");
        map.put("code", 500);
        return map;
    }


}
