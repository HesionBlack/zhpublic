package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class DeviceModel extends DeviceMappingModel {
    public List<TagModel> paramList;
    public String errorState;
    public String alarmState;
}