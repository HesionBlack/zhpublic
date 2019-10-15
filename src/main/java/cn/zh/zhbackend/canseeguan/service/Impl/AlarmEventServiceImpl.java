package cn.zh.zhbackend.canseeguan.service.Impl;/**
 * ClassName: AlarmEventService <br/>
 * Description: <br/>
 * date: 2019/10/11 下午1:47<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.Utils.readExcel;
import cn.zh.zhbackend.canseeguan.dao.AlarmDao;
import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.DataModule;
import cn.zh.zhbackend.canseeguan.service.IAlarmEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
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
        List<alarm> alarms = alarmDao.GetAlarmList(listQueryModel);
        for (alarm alarm :
                alarms) {
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
        List<DeviceModel> deviceList = new ArrayList<>(3);
        List<String> tagIds = new ArrayList<>();
        Map<Integer, TagMappingModel> dicTagDeviceMapping = AlarmEventServiceImpl.dicTagDeviceMapping;


        if (!(dicTagDeviceMapping.isEmpty()) && null!= dicTagDeviceMapping ) {
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
                            log.info(key+":"+value);
                            log.info("设备对比完毕");
                        }
                    }

                });
                List<tag> tags = alarmDao.GetDevices();
                List<TagModel> tagModels = new ArrayList<>();
                //对比完成以后,设备详细信息，存入结果集
                for (Map.Entry<Integer, TagMappingModel> entry : mappingResult.entrySet()) {
                    Integer key1 = entry.getKey();
                    TagMappingModel value1 = entry.getValue();
                    log.info("mappingResult:" + key1 + ":" + value1);

                    DeviceModel device = new DeviceModel();
                    device.buildingId = value1.buildingId;
                    device.floorId = value1.floorId;
                    device.roomId = value1.roomId;
                    device.deviceId = value1.deviceId;
                    device.deviceName = value1.deviceName;
                    device.deviceType = value1.deviceType;
                    device.deviceMapString = value1.deviceMapString;

                    TagModel tagModel = new TagModel();
                    tagModel.tagId = value1.tagId;
                    tagModel.tagKey = value1.tagKey;
                    tagModel.tagName = value1.tagName;
                    System.out.println(tagModel.toString());
                    tagIds.add(tagModel.tagId);
                    tempTags.put(tagModel.tagId, tagModel);
                    tagModels.add(tagModel);
                    device.paramList = tagModels.toArray(new TagModel[2]);
                    //向数据库查询温度数据

                    for (tag tag : tags) {
                        System.out.println(tag.tagId + ":" + tag.val);
                        tempTags.forEach((key, value) -> {

                            if (key.equals(tag.tagId)) {
                                value.tagVal = tag.val;
                            }
                        });
                    }
                    deviceList.add(device);
                }





            }


        }


        return deviceList;
    }

}
