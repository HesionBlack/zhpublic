package cn.zh.zhbackend.canseeguan.controller;/**
 * ClassName: DataController <br/>
 * Description: <br/>
 * date: 2019/10/10 下午3:46<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.Impl.AlarmEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhbackend
 *
 * @description: A Controller about Equipment Data
 *
 * @author: zxb
 *
 * @create: 2019-10-10 15:46
 **/
@RestController
public class DataController {

    @Autowired
    private AlarmEventServiceImpl alarmEventService;
    ResModel resModel = new ResModel();
    Map<String,Object> map = new HashMap<>();
    //获取当前报警数量
//    @PostMapping("/data/getCurrentAlarmCount")
//    public ResModel getCurrentAlarmCount(){
//
//    }

    @PostMapping("/data/getAll")
    public ResModel getAll(){
        return alarmEventService.findallAlarm();
    }
    /*
    获取当前报警数量
     */
    @PostMapping("/data/getCurrentAlarmCount")
    public Map GetCurrentAlarmCount(){
        map.put("code",200);
        map.put("isSuccess",true);
        map.put("message","success");
        ResModel res = alarmEventService.GetCurrentAlarmCount();
        System.out.println("Controller GetCurrentAlarmCount："+res);
        map.put("data",res.getData());
        return map;
    }

    @PostMapping("/data/getQueryAlarms")
    public Map getQueryAlarms(@RequestBody ListQueryModel listQueryModel) throws Exception {
//        测试获取参数
//        System.out.println(listQueryModel);
//        for (WhereCondition list:listQueryModel.getWhereConditions()
//             ) {
//            System.out.println(list);
//        }
        PagedDataModel pagedDataModel = new PagedDataModel();
        map.put("code",200);
        map.put("isSuccess",true);
        map.put("message","成功获取数据.");
        List<AlarmModel> alarmModels = alarmEventService.GetAlarmList(listQueryModel);
        pagedDataModel.setTotalCount(alarmModels.size());
        pagedDataModel.setData(alarmModels);

        map.put("data",pagedDataModel);
        return map;
    }
    //获取设备信息
    @PostMapping("/data/getEqpData")
    public Map getEqpData(@RequestBody String[] deviceIds){
        List<DeviceModel> deviceModels = alarmEventService.GetDevices(deviceIds);
        map.put("code",200);
        map.put("isSuccess",true);
        map.put("message","成功获取数据.");
        map.put("data",deviceModels);
        return map;
    }
}
