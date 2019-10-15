package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class WhereCondition
{
    public String field;
    public WhereConditionMethod method;
    public String[] value;
}
