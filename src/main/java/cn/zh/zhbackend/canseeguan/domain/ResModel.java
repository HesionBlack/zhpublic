package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class ResModel {
    /// <summary>
    /// 是否成功
    /// </summary>
    public Boolean isSuccess;

    /// <summary>
    /// 消息
    /// </summary>
    public String message;

    /// <summary>
    /// 数据
    /// </summary>
    public Object data;

    /// <summary>
    /// 编码
    /// </summary>
    public int code;


}