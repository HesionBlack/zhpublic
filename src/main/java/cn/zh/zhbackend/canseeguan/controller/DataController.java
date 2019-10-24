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
import cn.zh.zhbackend.canseeguan.service.DataService;
import cn.zh.zhbackend.canseeguan.service.Impl.AlarmEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.CellEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.DocumentServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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
        map.put("message", "成功获取数据....");
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
        path = ymlConfig.getPdfRootPath() + path;
        try {
            if (path != null) {
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
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
        }
        return map;
<<<<<<< HEAD
=======
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
                res.message = "成功获取数据...";
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
        public ResModel getTestTags (HttpServletResponse response)
        {
            String token = TokenUtils.getToken("rawBoxInfo.id");
            //设置请求头
            response.setHeader("authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "authorization");
            return dataService.getTestTags();
        }

    }


    /**
     * 获取电子文件PDF档
     *
     * @param eDocId
     * @param response
     */
    @GetMapping(value = "/data/getPdfShowFile/{eDocId}", produces = "application/json")
    public void getPdfShowFile(@PathVariable String eDocId, HttpServletResponse response) {

        {   //获取本地路径
            String rootPath = appconfig.getPdfRootPath() == null ? "D:" : appconfig.getPdfRootPath();
            String path = dataService.getViewPdfName(eDocId);
            //获取文件的完整路径
            String filePath = rootPath + path;
            File file = new File(filePath);
            ResModel res = dataService.getPdfShowFile(eDocId);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + res.message);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }
}

