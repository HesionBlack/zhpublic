package cn.zh.zhbackend.canseeguan.service;/**
 * ClassName: DataModule <br/>
 * Description: <br/>
 * date: 2019/10/15 上午8:38<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.dao.CellsqlDao;
import cn.zh.zhbackend.canseeguan.domain.CellMappingModel;
import cn.zh.zhbackend.canseeguan.domain.TagMappingModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-15 08:38
 **/
public class DataService {
    @Autowired
    private CellsqlDao cellsqlDao;
    public static TagMappingModel[] tagMappingModels;

    public static Map<Integer, TagMappingModel> dicTagDeviceMapping;
    public static Map<Integer, CellMappingModel> dicCellMapping;


    public  void UpdateCellTickCache() {
        //查询数据库
        List<Map<String, Object>> maps = cellsqlDao.UpdateCellTickCache();
        String keyString="";
        try {
            //遍历结果集
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                String cellMapString = map.get("cellMapString").toString();
                String totalThick = map.get("totalThick").toString();
                Integer totalBoxes = ((Number) map.get("totalBoxes")).intValue();
                if (!cellMapString.isEmpty()) {

                    dicCellMapping.forEach((key, value) -> {
                        if (value.cellMapString.equals(cellMapString)) {
                            //匹配上CellMaping Excel表中数据的格子，对两个属性赋值
                            value.totalBoxThick = totalThick.isEmpty() ? 0d : Double.valueOf(totalThick);
                            value.totalBoxCount = totalBoxes;
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
