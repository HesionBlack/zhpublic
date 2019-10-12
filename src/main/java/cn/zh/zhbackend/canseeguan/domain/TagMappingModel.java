package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class TagMappingModel extends DeviceMappingModel {
    public String tagKey;
    public String tagId;
    public String tagName;
    public String tagUnit;
}