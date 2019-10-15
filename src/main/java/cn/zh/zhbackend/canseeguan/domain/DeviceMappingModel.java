package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class DeviceMappingModel {
    public String buildingId;
    public String floorId;
    public String roomId;
    public String deviceId;
    public String deviceName;
    public String deviceType;
    public String deviceMapString;
}