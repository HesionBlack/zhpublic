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

    public static Map<Integer, CellMappingModel> dicCellMapping;

    @Override
    public List<CabinetModel> GetCellSummary(CabinetModel cabinetModel) {
        List<CabinetModel> cabinetModels = new ArrayList<>();
        dicCellMapping.forEach(((key, value) -> {
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
        if (null != dicCellMapping) {
            CellEventServiceImpl.dicCellMapping.forEach((key, value) -> {
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
                    boxModel.cabinetId = cellModel.cabinetId;
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
    @Test
    public void testToTransfer(){
        double v = toTransfer("52mm");
        System.out.println(v);
    }



}
