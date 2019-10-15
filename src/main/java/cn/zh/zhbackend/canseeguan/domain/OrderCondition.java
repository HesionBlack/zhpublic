package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class OrderCondition
{
    public String field;
    public OrderConditionMethod method;
}