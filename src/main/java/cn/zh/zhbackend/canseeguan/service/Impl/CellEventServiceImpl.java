package cn.zh.zhbackend.canseeguan.service.Impl;/**
 * ClassName: CellEventServiceImpl <br/>
 * Description: <br/>
 * date: 2019/10/17 上午9:55<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.dao.CellsqlDao;
import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.DataModule;
import cn.zh.zhbackend.canseeguan.service.DataService;
import cn.zh.zhbackend.canseeguan.service.ICellEventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @program: zhbackend
 * @description:
 * @author: zxb
 * @create: 2019-10-17 09:55
 **/
@Slf4j
@Service
public class CellEventServiceImpl implements ICellEventService {

    @Autowired
    private CellsqlDao cellsqlDao;

    private DataService dataService;


    @Override
    public List<CabinetModel> GetCellSummary(CabinetModel cabinetModel) {
        List<CabinetModel> cabinetModels = new ArrayList<>();
        DataService.dicCellMapping.forEach(((key, value) -> {
            System.out.println("dicCellMapping:" + value);
            if (cabinetModel.buildingId.equals(value.buildingId)
                    && cabinetModel.floorId.equals(value.floorId)
                    && cabinetModel.roomId.equals(value.roomId)
                    && cabinetModel.cabinetId.equals(value.cabinetId)) {
                cabinetModels.add(value);
            }
        }));
        System.out.println("数据已经添加到列表中...");
        return cabinetModels;
    }

    @Override
    public List<BoxModel> getCellBoxes(CellModel cellModel) throws Exception {
        List<BoxModel> boxModelsList = new ArrayList<>();
        List<CellMappingModel> cellMappingModels = new ArrayList<>();
        if (null != DataService.dicCellMapping) {
            DataService.dicCellMapping.forEach((key, value) -> {
                if (value.buildingId.equals(cellModel.buildingId)
                        && value.floorId.equals(cellModel.floorId)
                        && value.roomId.equals(cellModel.roomId)
                        && value.cabinetId.equals(cellModel.cabinetId)
                        && value.cellId.equals(cellModel.cellId)) {
                    System.out.println(value.toString());
                    cellMappingModels.add(value);
                }
            });
            if (cellMappingModels.isEmpty()) {
                throw (new Exception("无法在配置中找到指定CELL, 需检查配置文件."));
            }


            CellMappingModel cellMappingModel = cellMappingModels.get(0);
            //访问数据库
            List<Map<String, Object>> boxModelS = cellsqlDao.getCellBoxes(cellMappingModel);
//            List<BoxModelS> boxModelS = cellsqlDao.getCellBoxes(cellMappingModel);
            //再查一次数据库，讲特定字段数据读出，并对应BoxModelS实体类
            List<BoxModelS> cellBoxestoBoxModelS = cellsqlDao.getCellBoxestoBoxModelS(cellMappingModel);
            //数据库查询到的数据需要经过处理，才能放入到BoxModel里
            for (int i = 0; i < boxModelS.size(); i++) {

                Map<String, Object> map1 = boxModelS.get(i);
                System.out.println("boxModelS_map1"+map1);

                    //对BoxModel的基础属性进行赋值
                    BoxModel boxModel = new BoxModel();
                    boxModel.buildingId = cellModel.buildingId;
                    boxModel.floorId = cellModel.floorId;
                    boxModel.roomId = cellModel.roomId;
//                    boxModel.cabinetId = cellModel.cabinetId;
                    boxModel.index = -1;
                    boxModel.thick = 0.05d;
                    boxModel.cellId = cellModel.cellId;
                    boxModel.cellWidth=0.0d;

                    boxModel.boxId = String.valueOf(cellBoxestoBoxModelS.get(i).boxId);
                    boxModel.boxName = cellBoxestoBoxModelS.get(i).boxName;
                    //获取字符串最后一个字符,并转换成数字
                    boxModel.index = getBoxIndex(cellBoxestoBoxModelS.get(i).index, cellMappingModel.cellMapString);
                    if (0==boxModel.thick){
                        boxModel.thick = toTransfer(cellBoxestoBoxModelS.get(i).thick);
                    }



                    boxModel.boxInfo = map1;
                    boxModelsList.add(boxModel);


            }

        }
        return boxModelsList;
    }

    @Override
    public List<DocumentModel> getQueryDocuments(ListQueryModel query) {
        String queryString="";
        List<DocumentModel> documents = new ArrayList<>(2);
        BoxModelS boxModelS = new BoxModelS();
        if(query.whereConditions.length > 0){
            //遍历whereCondition数组 取出变量值
            for (WhereCondition whereCondition:
                 query.whereConditions) {
                if(!(null==whereCondition.field)){
                    queryString=whereCondition.value.toString();
                }
            }
            int itemStart = query.pageIndex*query.pageItemCount;
            //数据库查询
            List<Map<String, Object>> queryDocuments = cellsqlDao.getQueryDocuments(queryString, query.pageIndex, query.pageItemCount, itemStart);
            System.out.println(queryDocuments);
            List<String> boxIds = new ArrayList<>(3);
            //遍历结果集
            for (int i = 0; i < queryDocuments.size(); i++) {
                DocumentModel document = new DocumentModel();
                Map<String, Object> map = queryDocuments.get(i);
                Integer id = ((Number) map.get("id")).intValue();
                document.documentId= id.toString();
                document.docInfo = map;
                documents.add(document);
                Integer boxid = ((Number) map.get("boxid")).intValue();
                boxIds.add(boxid.toString());
            }
            System.out.println(boxIds);
            //用于存放盒的相关信息
            List<BoxModel> boxList = new ArrayList<>();
            if(boxIds.size()>0){
                //拼接SQL语句的条件
                String sql ="";
                for (int i = 0; i < boxIds.size(); i++) {
                    sql=sql+boxIds.get(i)+",";
                }
                String sqlSub = sql.substring(0, sql.length() - 1);
                System.out.println("sql:"+sql+"sqlSub:"+sqlSub);
                //通过盒子的id向数据库查询
                List<Map<String, Object>> boxInfoById = cellsqlDao.getBoxInfoById(sqlSub);
                System.out.println("boxInfoById:"+boxInfoById);

                for (int i = 0; i < boxInfoById.size(); i++) {

                    CellMappingModel cellMapping =null;
                    Map<String, Object> map = boxInfoById.get(i);
                    String position = (String) map.get("position");
                    String posSub = position.substring(0, position.length() - 2);
                    Map<Integer, CellMappingModel> dicCellMapping = DataService.dicCellMapping;
                    for (int j = 0; j < dicCellMapping.size(); j++) {
                        CellMappingModel cellMappingModel = dicCellMapping.get(j);
                        if(cellMappingModel != null){
                            if(cellMappingModel.cellMapString.equals(posSub)){
                                cellMapping = cellMappingModel;
                            }
                        }
                    }
                    if(null != cellMapping ){
                        BoxModel box = new BoxModel();

                        box.buildingId = cellMapping.buildingId;
                        box.floorId = cellMapping.floorId;
                        box.roomId = cellMapping.roomId;
                        box.cabinetId = cellMapping.cabinetId;
                        box.cellId = cellMapping.cellId;
                        box.boxInfo=map;

                        box.boxId = String.valueOf(map.get("id"));
                        box.boxName = String.valueOf(map.get("boxcode"));
                        box.thick = 0;
                        if (0==box.thick){
                            box.thick = toTransfer(map.get("backwidth").toString());
                        }
                        boxList.add(box);
                    }


                }
                BoxModel boxModel = null;
                for (int i = 0; i < documents.size(); i++) {
                    for (int j = 0; j < boxList.size(); j++) {

                        Integer boxid = ((Number) documents.get(i).docInfo.get("boxid")).intValue();
                        String boxId = boxid.toString();
                        if((boxList.get(j).boxId).equals(boxId)){
                            boxModel = boxList.get(j);
                        }
                    }
                    if(null != boxModel){
                        documents.get(i).buildingId = boxModel.buildingId;
                        documents.get(i).floorId = boxModel.floorId;
                        documents.get(i).roomId = boxModel.roomId;
                        documents.get(i).cabinetId = boxModel.cabinetId;
                        documents.get(i).cellId = boxModel.cellId;
                        documents.get(i).boxId = boxModel.boxId;
                    }
                }
            }


        }

        return documents;
    }

    @Override
    public List<BoxModel> getQueryBoxes(ListQueryModel query) {
        String queryString="";
        //创建一个列表 存放盒子对象
        List<BoxModel> boxModels = new ArrayList<>(2);
        //从传入的参数中 提取相关信息
        //判断whereCondition是否为空
        if(query.whereConditions.length > 0 ){
            //从whereCondition中遍历出whereCondition
            for (WhereCondition whereCondition : query.whereConditions) {
                //判断field是否为空
                if (!(null == whereCondition.field)) {
                    //不为空则提取出值
                    queryString = whereCondition.value.toString();
                }
                int itemStart = query.pageIndex*query.pageItemCount;
                //提取出来的条件 传入数据访问层  从数据库查询数据
                List<Map<String, Object>> queryBoxesInfo = cellsqlDao.getQueryBoxesInfo(queryString, query.pageIndex, query.pageItemCount, itemStart);
                List<String> keyList = new ArrayList<>();
                //遍历结果集
                for (int i = 0; i < queryBoxesInfo.size(); i++) {
                    BoxModel boxModel = new BoxModel();
                    //获取List中的Map
                    Map<String, Object> map = queryBoxesInfo.get(i);
                    boxModel.boxInfo=map;
                    boxModel.boxName = String.valueOf(map.get("boxcode"));
                    Integer id = ((Number) map.get("id")).intValue();
                    String boxId = id.toString();
                    boxModel.boxId = boxId;
                    boxModels.add(boxModel);
                    String position = map.get("position").toString();
                    keyList.add(position.substring(0,position.length()-2));
                }
                //需要将查到的数据对应Excel表中的数据获取相关Box的相关信息
                for (int i = 0; i < boxModels.size(); i++) {
                    BoxModel boxModel = boxModels.get(i);
                    DataService.dicCellMapping.forEach((key,value)-> {
                        for (int j = 0; j < keyList.size(); j++) {
                            String s = keyList.get(j);
                            if(s.equals(value.cellMapString)){
                                boxModel.buildingId = value.buildingId;
                                boxModel.floorId = value.floorId;
                                boxModel.roomId = value.roomId;
                                boxModel.cabinetId = value.cabinetId;
                                boxModel.cellId = value.cellId;
                            }
                        }
                    });
                }

            }
            //
        }
        return boxModels;
    }

    @Override
    public List<CellMappingModel> searchCellbySpace(ListQueryModel query) {
        dataService.UpdateCellTickCache();
        List<CellMappingModel> cellMappingModels = new ArrayList<>(0);
        try {
            if (query.whereConditions != null) {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cellMappingModels;
    }

    @Override
    public List<CabinetModel> getCellSummaryLimitSpace(CabinetModel cabinetModel,Double space) {
        List<CabinetModel> cabinetModels = new ArrayList<>();
        DataService.dicCellMapping.forEach(((key, value) -> {
            System.out.println("dicCellMapping:" + value);
            if (cabinetModel.buildingId.equals(value.buildingId)
                    && cabinetModel.floorId.equals(value.floorId)
                    && cabinetModel.roomId.equals(value.roomId)
                    && cabinetModel.cabinetId.equals(value.cabinetId)&&space <=(value.cellWidth-value.totalBoxThick)) {
                cabinetModels.add(value);
            }
        }));
        System.out.println("数据已经添加到列表中...");
        return cabinetModels;
    }


    private double toTransfer(String thick) {
        Double dThick = Double.valueOf(thick.replace("m", "").trim());
//        dThick = dThick/1000.00d;
        return (double)Math.round(dThick)/1000.00d;
    }
    private int getBoxIndex(String position, String cellMappingString) {
        Integer index = 0;
        String p = position.replace(cellMappingString, "").replace("-", "").trim();
        System.out.println("String p:" + p);
        if (!("".equals(p))) {
            index = Integer.valueOf(p);
        } else {
            return -1;
        }


        return index;
    }



}
