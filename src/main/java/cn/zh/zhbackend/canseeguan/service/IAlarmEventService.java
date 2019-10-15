package cn.zh.zhbackend.canseeguan.service;

import cn.zh.zhbackend.canseeguan.domain.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IAlarmEventService <br/>
 * Description: <br/>
 * date: 2019/10/11 下午1:45<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
public interface IAlarmEventService {
    public ResModel findallAlarm();
    public ResModel GetCurrentAlarmCount();
    public List<AlarmModel> GetAlarmList(ListQueryModel listQueryModel) throws Exception;
    public List<DeviceModel> GetDevices(String[] deviceIds);
}

