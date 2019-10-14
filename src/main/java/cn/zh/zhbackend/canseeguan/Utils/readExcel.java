package cn.excel.readexcel;/**
 * ClassName: readExcel <br/>
 * Description: <br/>
 * date: 2019/10/12 下午3:23<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: readexcel
 * @description:
 * @author: zxb
 * @create: 2019-10-12 15:23
 **/
@Slf4j
public class readExcel {
    @Test
    public void write() throws Exception {
//        Workbook excel1997 = new HSSFWorkbook(); // excel 1997
//        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//        excel1997.write(fileOut);
//        fileOut.close();

        Workbook wb = new XSSFWorkbook(); // excel 2007
        CreationHelper createHelper = wb.getCreationHelper();
        //创建excel文件
        FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
        //创建一个工作表
        Sheet sheet1 = wb.createSheet("new sheet");
        //向工作表里添加数据
        //创建列
        Row row = sheet1.createRow((short) 0);
        //创建行
        Cell cell = row.createCell(0);
        cell.setCellValue(1);
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue(
                createHelper.createRichTextString("This is a string"));
        row.createCell(3).setCellValue(true);
        wb.write(fileOut);
        fileOut.close();
    }

    @Test
    public void read(String filename) throws Exception {

        InputStream inputStream = null;
        int j = 0;

        try {
            //打开excel文件
            inputStream = this.getClass().getResourceAsStream("/static/" + filename);
            //创建一个表格输入流
            Workbook wb = WorkbookFactory.create(inputStream);
            //获取第一张工作表
            Sheet sheet = wb.getSheetAt(0);
            //Row类型的迭代器  用于遍历访问每一列
            Iterator<Row> rowIterator = sheet.rowIterator();

            //循环迭代开始
            Map<Integer,TagMappingModel> map = new HashMap<>();
            while (rowIterator.hasNext()) {
                System.out.println("第j:" + j + "次循环开始");
                if (j < 5) {
                    System.out.println("该行已经跳过");
                    j++;
                    //读取一列
                    Row r = rowIterator.next();
                    continue;
                }
                //读取一列
                Row r = rowIterator.next();
                //判断是否为空，若为空 则进入下一列
                if (r == null) {
                    System.out.println("Empty");
                    continue;
                }
                //开始循环遍历列中的每一行
                //从第一行开始，遍历到最后一行
                for (int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++) {
                    Cell cell = r.getCell(i);
                    String cellValue = "";
                    System.out.println("第i:" + i + "次循环开始");
                    map.put(j,new TagMappingModel());
                    switch ((cell.getCellType())) {
                        case STRING:
                            cellValue = cell.getRichStringCellValue().getString();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = cell.getDateCellValue().toString();
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            cellValue = String.valueOf(cell.getCellFormula());
                            break;
                        case BLANK:
                            break;
                        default:
                    }

                    //进行相应操作
                    System.out.println("CellNum:" + i + " => CellValue:" + cellValue);
                    switch (i) {
                        case 0:
                            map.get(j).setBuildingId(cellValue);
                            break;
                        case 1:
                            map.get(j).setFloorId(cellValue);
                            break;
                        case 2:
                            map.get(j).setRoomId(cellValue);
                            break;
                        case 3:
                            map.get(j).setDeviceId(cellValue);
                            break;
                        case 4:
                            map.get(j).setDeviceName(cellValue);
                            break;
                        case 5:
                            map.get(j).setDeviceType(cellValue);
                            break;
                        case 6:
                            map.get(j).setDeviceMapString(cellValue);
                            break;
                        case 7:
                            map.get(j).setTagKey(cellValue);
                            break;
                        case 8:
                            map.get(j).setTagId(cellValue);
                            break;
                        case 9:
                            map.get(j).setTagName(cellValue);
                            break;
                        case 10:
                            map.get(j).setTagUnit(cellValue);
                            break;
                    }

                }
                j++;

            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }


    }

    @Test
    public void testreadexcel() {
        TagMappingModel[] tagMappingModel = DataModule.tagMappingModel;
        DataModule.InitTagMapping("TagMapping.xlsx");
        for (int i = 0; i < tagMappingModel.length; i++) {
            System.out.println(tagMappingModel[i]);
        }
    }

    @Test
    public void readTag() {
        TagMappingModel[] tagMappingModel = DataModule.tagMappingModel;
        for (int i = 0; i < tagMappingModel.length; i++) {
            System.out.println(tagMappingModel[i]);
        }
    }

    /*InputStream inp = null;
    try {
        inp = new FileInputStream("workbook04.xls");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row r = rowIterator.next();
            if (r == null) {
                System.out.println("Empty Row");
                continue;
            }
            for (int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++) {
                Cell cell = r.getCell(i);
                String cellValue = "";
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = cell.getDateCellValue().toString();
                        } else {
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        cellValue = String.valueOf(cell.getCellFormula());
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    default:
                }
                System.out.println("CellNum:" + i + " => CellValue:" + cellValue);
            }
        }
    } finally {
        if (inp != null) {
            inp.close();
        }
    }*/
}
