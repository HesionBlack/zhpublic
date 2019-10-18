package cn.zh.zhbackend.canseeguan.domain;

import cn.zh.zhbackend.canseeguan.Utils.ConvertLongToDateTime;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class AlarmModel extends  TagMappingModel
    {
        public String title ;
        public String eventTime ;
        public String state ;
        public int priority ;
        public Alarm rawData ;

        public AlarmModel(Alarm rawData, TagMappingModel tagMapping)
        {
                this.rawData = rawData;
                this.title = tagMapping.tagName + "（报警类型：" + rawData.AlarmType + " , "
                        + "报警值：" + rawData.EventValue + " , "
                        + "报警限值：" + rawData.LimitValue + "）";
                this.eventTime = ConvertLongToDateTime.convertTimeToString(rawData.EventTime);
                this.state = rawData.AlarmState;
                this.priority = rawData.AlarmPriority;
                this.buildingId = tagMapping.buildingId;
                this.floorId = tagMapping.floorId;
                this.roomId = tagMapping.roomId;
                this.deviceId = tagMapping.deviceId;
                this.deviceName = tagMapping.deviceName;
                this.deviceType = tagMapping.deviceType;
                this.deviceMapString = tagMapping.deviceMapString;
                this.tagId = tagMapping.tagId;
                this.tagKey = tagMapping.tagKey;
                this.tagName = tagMapping.tagName;
                this.tagUnit = tagMapping.tagUnit;

            }

    }