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


    @Autowired
    private DataDao dataDao;

    public static BoxModel GetBoxDetailInfoByIdAndPosition(String id, String cellPos) {
        BoxModel resModel = new BoxModel();
        resModel.setBoxId(id);
        DataEntity.dicCellMapping.forEach((key, value) -> {
            if (value.cellMapString.equals(cellPos)) {
                var item = DataEntity.dicCellMapping.get(cellPos);
                resModel.buildingId = item.buildingId;
                resModel.floorId = item.floorId;
                resModel.roomId = item.roomId;
                resModel.cabinetId = item.cabinetId;
                resModel.cellId = item.cellId;
            }
        });


        return resModel;
    }


    /**
     * 获取盒全部文档信息
     *
     * @param query
     * @return
     */
    public ResModel GetDocumentsbyBoxId(ListQueryModel query) {
        ResModel res = new ResModel();
        res.setCode(200);
        long boxid = 0;
        int tCount = 0;
        try {

            if (query.whereConditions.length > 0) {
                for (WhereCondition condition : query.whereConditions) {
                    if (condition.field == "boxId" && condition.field != null) {
                        boxid = Long.parseLong(condition.value.toString());
                    }
                    DocumentModel[] rep = dataDao.GetDocumentsbyBoxId(boxid, query.pageIndex, query.pageItemCount, tCount).toArray(new DocumentModel[0]);
                    res.data = new PagedDataModel();
                    res.setData(tCount);
                    res.data = rep;
                    res.isSuccess = true;
                    res.message = "成功获取数据.";
                }
                res.isSuccess = false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *获取测试参数信息
     * @return
     */
    public  ResModel getTestTags() {
        ResModel res = new ResModel();
        res.setCode(200);
        try {
            Random rnd = new Random();
            List<TestTag> tagList = new ArrayList<>();
            List<String> tags = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                tags.add("t_00" + i);
                tags.add("h_00" + i);
            }


            for (String testTag : tags) {
                Random rnd_t = new Random();
                Random rnd_h = new Random();
                String tval = String.valueOf((rnd_t.nextInt(21) + 240) / 10d);
                String hval = String.valueOf((rnd_t.nextInt(21) + 390) / 10d);
                TestTag tag = new TestTag();
                tag.fid = testTag;
                tag.tid = "1";
                tag.val = testTag.contains("t") ? tval : hval;
                tagList.add(tag);
            }
            int _rnd = rnd.nextInt(2)+1;
            if (_rnd == 1) {
                throw new Exception("异常测试");
            }
            res.data = tagList;
            res.isSuccess = true;
            res.message = "成功获取数据.";
        } catch (Exception ex) {
            res.message=ex.getMessage();
            res.isSuccess = false;
        }

        return res;
    }

    public String getViewPdfName(String edocId) {

        String path = "";
        List<Map<String, Object>> filePath = dataDao.getFilePath(edocId);
        for (int i = 0; i < filePath.size(); i++) {
            Map<String, Object> map = filePath.get(i);
            String filepath = (String) map.get("filepath");
            String savefilename = (String) map.get("savefilename");
            String extension = (String) map.get("extension");
            path = filepath.replace("/", "\\") + savefilename.replace("." + extension, "_show.pdf");
        }
        return path;
    }

    /**
     * 获取电子文件PDF档
     *
     * @param eDocId
     * @return
     */
    public ResModel getPdfShowFile(String eDocId) {
        ResModel res = new ResModel();
        res.setCode(200);
        try {
            String rootPath = appconfig.getPdfRootPath() == null ? "D:" : appconfig.getPdfRootPath();
            String path = getViewPdfName(eDocId);
            String filePath = rootPath + path;
            File file = new File(filePath);
            //BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            if (path != null && file.exists()) {

                res.data =br.lines();
                res.message = file.getName();
                res.isSuccess = true;
            } else {
                res.data = null;
                res.message = "不存在PDF电子档文件";
                res.isSuccess = false;
            }
        } catch (Exception ex) {
            res.data = null;
            res.message = ex.getMessage();
            res.isSuccess = false;
        }

        return res;
    }

}
