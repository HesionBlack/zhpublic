package cn.zh.zhbackend.canseeguan.controller;/**
 * ClassName: DataController <br/>
 * Description: <br/>
 * date: 2019/10/10 下午3:46<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.Config.YmlConfig;
import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.ICellEventService;
import cn.zh.zhbackend.canseeguan.service.Impl.AlarmEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.CellEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.DocumentServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.FileServiceImpl;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
    private FileServiceImpl fileService;
    @Autowired
    private YmlConfig ymlConfig;
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

    /*
    获取报警设备信息
     */
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

    /*
    获取设备信息
     */
    @PostMapping("/data/getEqpData")
    public Map getEqpData(@RequestBody String[] deviceIds) {
        List<DeviceModel> deviceModels = alarmEventService.GetDevices(deviceIds);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", deviceModels);
        return map;
    }

    /*
    获取节统计数据
     */
    @PostMapping("/data/getCellSummary")
    public Map GetCellSummary(@RequestBody CabinetModel cabinetModel, @PathVariable @Nullable Double space) {
        List<CabinetModel> cabinetModels = null;
        if (space == null) {
            cabinetModels = cellEventService.GetCellSummary(cabinetModel);
        } else if (space >= 0.0d) {
            cabinetModels = cellEventService.getCellSummaryLimitSpace(cabinetModel, space);
        }
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", cabinetModels);
        return map;
    }

    /*
    获取节全部BOX信息
     */
    @PostMapping("/data/getCellBoxes")
    public Map getCellBoxes(@RequestBody CellModel cellModel) throws Exception {

        List<BoxModel> boxModels = cellEventService.getCellBoxes(cellModel);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        map.put("data", boxModels);
        return map;
    }

    /*
    获取盒全部文档信息
     */
    @PostMapping("/data/getBoxDocuments")
    public Map getDocumentsbyBoxId(@RequestBody ListQueryModel query) throws Exception {
        PagedDataModel pagedDataModel = new PagedDataModel();
        map.put("code", 200);
        List<DocumentModel> documentsByBoxId = null;
        long boxId = 0;
        if (query.whereConditions.length > 0) {
            for (WhereCondition whereCondition :
                    query.whereConditions) {
                if ("boxId".equals(whereCondition.field) && !("".equals(whereCondition.field))) {
                    boxId = Long.parseLong(whereCondition.value.toString());
                }
            }

            documentsByBoxId = documentService.getDocumentsByBoxId(boxId, query.pageIndex, query.pageItemCount);
        }

        map.put("isSuccess", true);
        map.put("message", "成功获取数据.");
        pagedDataModel.setTotalCount(documentsByBoxId.size());
        pagedDataModel.setData(documentsByBoxId);
        map.put("data", pagedDataModel);
        return map;
    }

    /*
    获取查询文档信息
     */
    @PostMapping("/data/getQueryDocuments")
    public Map getQueryDocuments(@RequestBody ListQueryModel query) throws Exception {
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

    /*
    获取条件查询档案盒信息
    @return
     */
    @PostMapping("/data/getQueryBoxes/")
    public Map getQueryBoxes(@RequestBody ListQueryModel query) throws Exception {
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


    /*
        获取查询剩余空间  //..
        @return
     */
    @PostMapping("/data/searchCellBySpace")
    public Map searchCellbySpace(ListQueryModel query) {
        map.put("code", 200);
        map.put("isSuccess", true);
        List<CellMappingModel> cellMappingModels = cellEventService.searchCellbySpace(query);
        map.put("message", "成功获取数据.");
        map.put("data", 2);
        return map;
    }


    /*
        获取测试报警信息
        @return
     */
    @PostMapping("/data/getTestAlarm")
    public Map getTestAlarms() {
        map.put("code", 200);
        map.put("isSuccess", true);
        List<TestAlarmModel> testAlarms = AlarmEventServiceImpl.getTestAlarms();
        map.put("data", testAlarms);
        map.put("message", "成功获取数据.");
        return map;
    }

    //..
    /*
        获取电子文件是否存在PDF档

     */
    @GetMapping("/data/checkPdfShowFileExist/{eDocId}")
    public Map CheckPdfShowFileExist(@PathVariable String eDocId) {
        map.put("code", 200);

        String path = fileService.getViewPdfName(eDocId);
        path = ymlConfig.getPdfRootPath()+path;
        try {
            if(path != null) {
                File testFile = new File(path);
                if (!testFile.exists()) {
                    System.out.println("文件不存在");
                    map.put("data", false);
                    map.put("message", "该文件不存在PDF档");
                } else {
                    map.put("data", true);
                    map.put("message", "该文件存在PDF档");
                }
                map.put("isSuccess", true);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

}


