package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class User {
    public String _id;

    public String userId;

    public String userName;

    public String userPw;

    public String userPwNew;

    public String userPwRepeat;

}
