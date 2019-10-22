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
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private DataService dataService;
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
    public Map GetCellSummary(@RequestBody CabinetModel cabinetModel, @PathVariable @Nullable Double space) {
        List<CabinetModel> cabinetModels=null;
        if(space.isNaN()) {
            cabinetModels = cellEventService.GetCellSummary(cabinetModel);
        }else if (space >= 0.0d){
            cabinetModels = cellEventService.getCellSummaryLimitSpace(cabinetModel,space);
        }
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

    //获取条件查询报警信息
    @PostMapping("/data/searchCellBySpace")
    public Map searchCellbySpace(ListQueryModel query){
        map.put("code", 200);
        map.put("isSuccess", true);
        List<CellMappingModel> cellMappingModels = cellEventService.searchCellbySpace(query);
        map.put("message", "成功获取数据.");
        map.put("data", 2);
        return map;
    }

    /**
     *根据id和position获取查询档案盒位置等信息
     * @param rawBoxInfo
     * @param response
     * @return
     */
    @PostMapping(value = "/data/GetBoxDetailInfoByIdAndPosition", produces = "application/json")
    public static ResModel GetBoxDetailInfoByIdAndPosition (@RequestBody BoxInfo rawBoxInfo, HttpServletResponse
            response){
        String token = TokenUtils.getToken("rawBoxInfo.id");
        //设置请求头
        response.setHeader("authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "authorization");
        ResModel res = new ResModel();
        res.setCode(200);

        if (null == rawBoxInfo) {
            res.message = "未提供有效的数据";
        }

        if (0 == rawBoxInfo.id || null == rawBoxInfo.position) {
            System.out.println(rawBoxInfo.position);
            System.out.println(rawBoxInfo.id);
            System.out.println(rawBoxInfo.backwidth);
            System.out.println(rawBoxInfo.boxcode);
            res.message = "必须提供档案盒 id 和 位置信息 (position)";
            return res;
        }

        try {
            int len = rawBoxInfo.position.lastIndexOf('-');

            if (rawBoxInfo.position.length() <= 2 && len < 0) {

                res.message = "位置信息 (position) 格式不正确 [" + rawBoxInfo.position + "]";
                return res;
            }


            String cellPos = rawBoxInfo.position.substring(0, len);

            DataEntity.dicCellMapping.forEach((key, value) -> {
                if (!value.cellMapString.equals(cellPos)) {
                    res.message = "cell 位置信息 (cellMapString) 信息不存在 [" + cellPos + "]";
                }
            });

            res.data = DataService.GetBoxDetailInfoByIdAndPosition(String.valueOf(rawBoxInfo.id), cellPos);
            res.message = "成功获取数据.";
            res.isSuccess = true;
            return res;
        } catch (Exception ex) {
            res.message = ex.getMessage();
            res.isSuccess = false;
            ex.printStackTrace();
        }

        return res;
    }


    /**
     * 获取测试参数信息
     * @return
     */
    @PostMapping(value = "/data/getTestTag", produces = "application/json")
    public ResModel getTestTags(HttpServletResponse response)
    {
        String token = TokenUtils.getToken("rawBoxInfo.id");
        //设置请求头
        response.setHeader("authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "authorization");
        return dataService.getTestTags();
    }

}


