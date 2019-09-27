package cn.zh.zhbackend.canSeeGuan.dao;


import cn.zh.zhbackend.canSeeGuan.Utils.AESUtil;
import cn.zh.zhbackend.canSeeGuan.domain.ResModel;
import cn.zh.zhbackend.canSeeGuan.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                          Criteria.where("userId").is(user.getUserId())

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
}
