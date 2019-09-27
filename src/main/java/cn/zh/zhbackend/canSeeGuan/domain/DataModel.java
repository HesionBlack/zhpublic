package cn.zh.zhbackend.canSeeGuan.domain;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DataModel {


    public static void main(String[] args) {
        DataModel dataModel = new DataModel();
        dataModel.InitCellMapping();
    }
    public  String InitCellMapping(){
        String outMsg=null;
        try {
            File FileUrl = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/CellMapping.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(ResourceUtils.getFile(FileUrl.toString())));
            //Excel工作表
            XSSFSheet sheetAt = wb.getSheetAt(0);

            //表头那一行
            XSSFRow titleRow = sheetAt.getRow(0);

            //表头那个单元格
            XSSFCell titleCell = titleRow.getCell(0);

            String title = titleCell.getStringCellValue();

            System.out.println("标题是："+title);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outMsg;
    }
}
