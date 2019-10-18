package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class WhereCondition
{
    public Object field;
    public WhereConditionMethod method;
    public Object value;
}
