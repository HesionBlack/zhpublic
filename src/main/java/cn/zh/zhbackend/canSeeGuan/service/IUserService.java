package cn.zh.zhbackend.canSeeGuan.service;

import cn.zhonghuan.canSeeGuan.domain.ResModel;
import cn.zhonghuan.canSeeGuan.domain.User;

import java.util.List;

public interface IUserService {
    public ResModel login(User user) throws Exception;
    public List<User> findAll();

    ResModel add(User user);
}
