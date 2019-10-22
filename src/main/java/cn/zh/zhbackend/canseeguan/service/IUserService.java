package cn.zh.zhbackend.canseeguan.service;


import cn.zh.zhbackend.canseeguan.domain.ResModel;
import cn.zh.zhbackend.canseeguan.domain.User;

import java.util.List;

public interface IUserService {
    public ResModel login(User user) throws Exception;
    public List<User> findAll();
    ResModel add(User user);


    /**
     * 用户登录
     *
     * @param userId
     * @return
     */
    User findByUserId(String userId);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    String addUser(User user);

    /**
     * 修改用户密码
     *
     * @param user
     * @return
     */
    String updateUser(User user);

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    String getToken(String userId);
}
