package cn.zh.zhbackend.canseeguan.service;


import cn.zh.zhbackend.canseeguan.domain.ResModel;
import cn.zh.zhbackend.canseeguan.domain.User;

import java.util.List;

public interface IUserService {
    public ResModel login(User user) throws Exception;
    public List<User> findAll();

    ResModel add(User user);
}
