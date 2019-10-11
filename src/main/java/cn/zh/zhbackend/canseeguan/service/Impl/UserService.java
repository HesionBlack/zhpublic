package cn.zh.zhbackend.canSeeGuan.service.Impl;


import cn.zh.zhbackend.canSeeGuan.dao.UserDao;
import cn.zh.zhbackend.canSeeGuan.domain.ResModel;
import cn.zh.zhbackend.canSeeGuan.domain.User;
import cn.zh.zhbackend.canSeeGuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    ResModel res = new ResModel();
    @Override
    public ResModel login(User user) throws Exception {
         List<User> isloginSuccess = userDao.login(user);
        if(!isloginSuccess.isEmpty()){
            res.setCode(200);
            return res;
        }
        res.setCode(500);
        return res;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public ResModel add(User user) {
        return userDao.add(user);
    }
}
