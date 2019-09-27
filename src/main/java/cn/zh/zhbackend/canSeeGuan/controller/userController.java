package cn.zh.zhbackend.canSeeGuan.controller;


import cn.zh.zhbackend.canSeeGuan.Utils.AjaxResponse;
import cn.zh.zhbackend.canSeeGuan.domain.ResModel;
import cn.zh.zhbackend.canSeeGuan.domain.User;
import cn.zh.zhbackend.canSeeGuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class userController {

    @Autowired
    private IUserService userService;

    @PostMapping("/zh/login")
    public AjaxResponse login(@RequestBody User user) throws Exception {
        System.out.println(user);
        ResModel login = userService.login(user);
        if (login.getCode()==200){
            return AjaxResponse.success(user);
        }
        return AjaxResponse.fail();
    }
    @PostMapping("/zh/findall")
    public List<User> findAll() {
        return userService.findAll();
    }


    @PostMapping("/zh/add")
    public AjaxResponse Add(@RequestBody User user) throws Exception {
        System.out.println(user);
        ResModel login = userService.add(user);
        if (login.getCode()==200){
            return AjaxResponse.success(user);

        }
        return AjaxResponse.fail();
    }
}
