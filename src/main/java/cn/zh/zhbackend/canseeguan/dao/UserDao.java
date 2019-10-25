package cn.zh.zhbackend.canseeguan.dao;


import cn.zh.zhbackend.canseeguan.Utils.AESUtil;
import cn.zh.zhbackend.canseeguan.domain.ResModel;
import cn.zh.zhbackend.canseeguan.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> login(User user) throws Exception {
        System.out.println("Dao---u"+user);
        String pwd = AESUtil.aesEncrypt(user.getUserPw(), "zhonghuan13xxxxx");
        System.out.println("Dao---pw---"+pwd);
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                          Criteria.where("userId").is(user.getUserId()),
                          Criteria.where("userPw").is(pwd)
                ));
        List<User> users = mongoTemplate.find(query,User.class);
        System.out.println(user);
        return users;
    }
    /**
     * 查询所有
     * @return
     */
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public ResModel add(User user) {
        ResModel res = new ResModel();
        try {
            String pwd = AESUtil.aesEncrypt(user.getUserPw(), "zhonghuan13xxxxx");
            user.setUserPw(pwd);
            mongoTemplate.insert(user);
            res.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(500);
        }


        return res;
    }


    /**
     * 用户登录
     *
     * @param userId
     * @return
     */
    public User findByUserId(String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
        User user = mongoTemplate.findOne(query, User.class);
        System.out.println("dao" + user);
        return user;
    }


    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public String addUser(User user) {
        mongoTemplate.save(user);

        return "添加成功";
    }

    /**
     * 修改用户密码
     *
     * @param user
     * @return
     */
    public String updateUser(User user) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        System.out.println("changedao:" + user);
        Update update = new Update().set("UserPw", user.getUserPwNew());
        mongoTemplate.updateFirst(query, update, User.class);

        return "修改成功";
    }


}
