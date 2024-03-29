package cn.zh.zhbackend.canseeguan.dao;


import cn.zh.zhbackend.canseeguan.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author Hxx
 */
@Repository
public interface IUserDao {
    /**
     * 根据用户名查找用户
     *
     * @param userId
     * @return
     */
    User findByUserId(String userId);

    /**
     * 添加用户
     * @param user
     * @return
     */
    String addUser(User user);

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    String updateUser(User user);
}