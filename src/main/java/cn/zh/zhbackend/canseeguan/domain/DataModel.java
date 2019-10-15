package cn.zh.zhbackend.canseeguan.domain;

import cn.zh.zhbackend.canseeguan.Utils.ExcelUtilsBySelf;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;


public class DataModel {


    public static void main(String[] args) throws Exception {
        DataModel dataModel = new DataModel();
        dataModel.InitCellMapping();
    }
    public  Boolean InitCellMapping() throws Exception {
        ExcelUtilsBySelf excelUtils = new ExcelUtilsBySelf();
        HashMap<Integer, CellModel> map = new HashMap<Integer,CellModel>();
        ReturnValue returnValue = new ReturnValue();
        //获取xlsx文件路径
        File FileUrl = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/CellMapping.xlsx");
//        excelUtils.ExcelToEntityList(FileUrl.toString(),map,returnValue);

        return false;
    }
}
