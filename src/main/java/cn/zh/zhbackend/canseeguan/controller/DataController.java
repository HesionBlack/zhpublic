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
import cn.zh.zhbackend.canseeguan.service.ICellEventService;
import cn.zh.zhbackend.canseeguan.service.Impl.AlarmEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.CellEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhbackend
 * @description: A Controller about Equipment Data
 * @author: zxb
 * @create: 2019-10-10 15:46
 **/
@RestController
public class DataController {

    @Autowired
    private AlarmEventServiceImpl alarmEventService;
    @Autowired
    private CellEventServiceImpl cellEventService;
    @Autowired
    private DocumentServiceImpl documentService;
    ResModel resModel = new ResModel();
    Map<String, Object> map = new HashMap<>();
    @PostMapping("/data/getAll")
    public ResModel getAll() {
        return alarmEventService.findallAlarm();
    }

    /*
    获取当前报警数量
     */
    @PostMapping("/data/getCurrentAlarmCount")
    public Map GetCurrentAlarmCount() {
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "success");
        ResModel res = alarmEventService.GetCurrentAlarmCount();
        System.out.println("Controller GetCurrentAlarmCount：" + res);
        map.put("data", res.getData());
        return map;
    }

    //获取报警设备信息
    @PostMapping("/data/getQueryAlarms")
    public Map getQueryAlarms(@RequestBody ListQueryModel listQueryModel) throws Exception {
        PagedDataModel pagedDataModel = new PagedDataModel();
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        List<AlarmModel> alarmModels = alarmEventService.GetAlarmList(listQueryModel);
        pagedDataModel.setTotalCount(alarmModels.size());
        pagedDataModel.setData(alarmModels);

        map.put("data", pagedDataModel);
        return map;
    }

    //获取设备信息
    @PostMapping("/data/getEqpData")
    public Map getEqpData(@RequestBody String[] deviceIds) {
        List<DeviceModel> deviceModels = alarmEventService.GetDevices(deviceIds);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", deviceModels);
        return map;
    }

    //获取节统计数据
    @PostMapping("/data/getCellSummary")
    public Map GetCellSummary(@RequestBody CabinetModel cabinetModel) {
        List<CabinetModel> cabinetModels = cellEventService.GetCellSummary(cabinetModel);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", cabinetModels);
        return map;
    }

    //获取节全部BOX信息
    @PostMapping("/data/getCellBoxes")
    public Map getCellBoxes(@RequestBody CellModel cellModel) throws Exception {

        List<BoxModel> boxModels = cellEventService.getCellBoxes(cellModel);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", boxModels);
        return map;
    }

    //获取盒全部文档信息
    @PostMapping("/data/getBoxDocuments")
    public Map getDocumentsbyBoxId(@RequestBody ListQueryModel query) throws Exception {

        map.put("code", 200);
        long boxId = 0;
        if (query.whereConditions.length > 0) {
            for (WhereCondition whereCondition :
                    query.whereConditions) {
                if ("boxId".equals(whereCondition.field) && !("".equals(whereCondition.field))) {
                    boxId = Long.parseLong(whereCondition.value.toString());
                }
            }
            documentService.getDocumentsByBoxId(boxId, query.pageIndex, query.pageItemCount);
        }

        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", 2);
        return map;
    }

    //获取查询文档信息
    @PostMapping("/data/getQueryDocuments")
    public Map getQueryDocuments(@RequestBody ListQueryModel query) throws  Exception{
        PagedDataModel pagedDataModel = new PagedDataModel();
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        List<DocumentModel> queryDocuments = cellEventService.getQueryDocuments(query);
        pagedDataModel.setTotalCount(queryDocuments.size());
        pagedDataModel.setData(queryDocuments);

        map.put("data", pagedDataModel);
        return map;
    }

    //获取条件查询档案盒信息
    @PostMapping("/data/getQueryBoxes/")
    public Map getQueryBoxes(@RequestBody ListQueryModel query) throws Exception{
        PagedDataModel pagedDataModel = new PagedDataModel();
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        //数据获取
        List<BoxModel> queryBoxes = cellEventService.getQueryBoxes(query);
        pagedDataModel.setTotalCount(queryBoxes.size());
        pagedDataModel.setData(queryBoxes);

        map.put("data", pagedDataModel);
        return map;

    }
}


