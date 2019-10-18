package cn.zh.zhbackend.canseeguan.service.Impl;/**
 * ClassName: AlarmEventService <br/>
 * Description: <br/>
 * date: 2019/10/11 下午1:47<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.dao.AlarmDao;
import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.IAlarmEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: zhbackend
 * @description:
 * @author: zxb
 * @create: 2019-10-11 13:47
 **/
@Slf4j
@Service
public class AlarmEventServiceImpl implements IAlarmEventService {

    @Autowired
    private AlarmDao alarmDao;

    public static TagMappingModel[] tagMappingModels;

    public static Map<Integer, TagMappingModel> dicTagDeviceMapping;


    @Override
    public ResModel findallAlarm() {
        return alarmDao.findallAlarm();
    }

    @Override
    public ResModel GetCurrentAlarmCount() {
        return alarmDao.getCurrentAlarmCount();
    }

    @Override
    public List<AlarmModel> GetAlarmList(ListQueryModel listQueryModel) throws Exception {
        List<AlarmModel> alarmModels = new ArrayList<AlarmModel>();
        Map<Integer, TagMappingModel> dicTagDeviceMapping = AlarmEventServiceImpl.dicTagDeviceMapping;
        List<Alarm> Alarms = alarmDao.GetAlarmList(listQueryModel);
        for (Alarm alarm :
                Alarms) {
            dicTagDeviceMapping.forEach((key, value) -> {
                alarmModels.add(new AlarmModel(alarm, value));
            });
        }

        System.out.println("GetAlarmList:" + alarmModels);
        log.info("INFO GetAlarmList:" + alarmModels);
        return alarmModels;
    }

    @Override
    public List<DeviceModel> GetDevices(String[] deviceIds) {

        Map<Integer, TagMappingModel> mappingResult = new HashMap<>();
        Map<String, TagModel> tempTags = new HashMap<>();
        List<DeviceModel> deviceList = new ArrayList<>();
        List<String> tagIds = new ArrayList<>();
        Map<Integer, TagMappingModel> dicTagDeviceMapping = AlarmEventServiceImpl.dicTagDeviceMapping;
        //遍历结果集
        Map<String, DeviceModel> mappingResultHandle = new HashMap<>();
        List<TagModel> tagModels = new ArrayList<>(2);
        if (!(dicTagDeviceMapping.isEmpty()) && null != dicTagDeviceMapping) {
            log.info("dicTagDeviceMapping has value");
            if (deviceIds.length > 0) {
                log.info("deviceId > 0 ");

                dicTagDeviceMapping.forEach((key, value) -> {
                    log.debug("dicTagDeviceMapping.value:" + value);
                    //从传入的设备ID数组和从excel文件里存有的设备ID对比
                    //并返回这些设备的详细信息
                    int length = deviceIds.length;
                    for (int i = 0; i < length; i++) {
                        if (value.deviceId.equals(deviceIds[i])) {
                            mappingResult.put(key, value);
                        }

                    }

                });
                log.info("设备对比完毕");
                //查询数据库
                List<Tag> tags = alarmDao.GetTagData();
                int i = 0;
                //设备信息进行赋值
                for (Map.Entry<Integer, TagMappingModel> tagMappingModelEntry : mappingResult.entrySet()) {
                    Integer key = tagMappingModelEntry.getKey();
                    TagMappingModel value = tagMappingModelEntry.getValue();
                    DeviceModel device = new DeviceModel();
                    device.buildingId = value.buildingId;
                    device.floorId = value.floorId;
                    device.roomId = value.roomId;
                    device.deviceId = value.deviceId;
                    device.deviceName = value.deviceName;
                    device.deviceType = value.deviceType;
                    device.deviceMapString = value.deviceMapString;
                    mappingResultHandle.put(device.deviceId, device);
                }


                //传感器设备进行赋值
                int n=0;
                for (Map.Entry<Integer, TagMappingModel> tagMappingModelEntry : mappingResult.entrySet()) {
                    Integer key = tagMappingModelEntry.getKey();
                    TagMappingModel value = tagMappingModelEntry.getValue();

                    if(n==2){
                        tagModels = new ArrayList<>(2);
                        n=0;
                    }
                    System.out.println("tagId:" + value.tagId);
                    TagModel tagModel = new TagModel();
                    if (value.tagId.startsWith("h_", 0)) {
                        System.out.println("湿度数据");
                        tagModel.tagId = value.tagId;
                        tagModel.tagKey = value.tagKey;
                        tagModel.tagName = value.tagName;
                        for (Tag tag : tags) {
                            if (tag.tagId.equals(value.tagId)) {
                                tagModel.tagVal = tag.val;
                            }
                        }

                    } else if (value.tagId.startsWith("t_", 0)) {
                        System.out.println("温度数据");

                        tagModel.tagId = value.tagId;
                        tagModel.tagKey = value.tagKey;
                        tagModel.tagName = value.tagName;
                        for (Tag tag : tags) {
                            if (tag.tagId.equals(value.tagId)) {
                                tagModel.tagVal = tag.val;
                            }
                        }


                    } else {
                        System.out.println("未知数据 ");
                    }
                    tagModels.add(tagModel);
                    mappingResultHandle.get(value.deviceId).setParamList(tagModels);
                    n++;
                }


            }
            mappingResultHandle.forEach((key,value)->{
                deviceList.add(value);
            });
        }
        return deviceList;
    }
}
