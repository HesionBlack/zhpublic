package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class PptParamFuncAttribute {
    public String Id;

    public String Name;

    public String Desc;

    public String Category;

    public int MinRefreshTime;

    public String ReturnFormat;

}