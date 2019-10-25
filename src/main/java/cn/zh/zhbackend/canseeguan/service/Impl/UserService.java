package cn.zh.zhbackend.canseeguan.service.Impl;


import cn.zh.zhbackend.canseeguan.Utils.JwtUtil;
import cn.zh.zhbackend.canseeguan.dao.UserDao;
import cn.zh.zhbackend.canseeguan.domain.ResModel;
import cn.zh.zhbackend.canseeguan.domain.User;
import cn.zh.zhbackend.canseeguan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    ResModel res = new ResModel();
    @Override
    public ResModel login(User user) throws Exception {
         List<User> isloginSuccess = userDao.login(user);
        System.out.println("Service"+isloginSuccess);
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


    /**
     * 用户登录
     *
     * @param userId
     * @return
     */
    @Override
    public User findByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public String addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user
     * @return
     */
    @Override
    public String updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * 生成token
     * @param userId
     * @return
     */
    @Override
    public String getToken(String userId) {

        //存入JWT的payload中生成token
        Map claims = new HashMap<String,Integer>();
        claims.put("user_userId",userId);
        String subject = "user";
        String token = null;
        try {
            //该token过期时间为12h
            token = JwtUtil.generateToken(userId);
        } catch (Exception e) {
            throw new RuntimeException("创建Token失败");
        }

        System.out.println("token:"+token);
        return token;
    }

}
