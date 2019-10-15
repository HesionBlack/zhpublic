package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DeviceModel extends DeviceMappingModel {
    public TagModel[] paramList;
    public String errorState;
    public String alarmState;
}