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

import java.util.List;
import java.util.Map;

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
        List<Map<String, Object>> maps = cellsqlDao.UpdateCellTickCache();
        String keyString="";
        try {
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                String cellMapString = map.get("cellMapString").toString();
                String totalThick = map.get("totalThick").toString();
                Integer totalBoxes = ((Number) map.get("totalBoxes")).intValue();
                if (!cellMapString.isEmpty()) {

                    dicCellMapping.forEach((key, value) -> {
                        if (value.cellMapString.equals(cellMapString)) {
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
