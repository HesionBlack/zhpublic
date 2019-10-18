package cn.zh.zhbackend.canseeguan.service;/**
 * ClassName: DataModule <br/>
 * Description: <br/>
 * date: 2019/10/15 上午8:41<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.Utils.readExcel;
import cn.zh.zhbackend.canseeguan.domain.CellMappingModel;
import cn.zh.zhbackend.canseeguan.domain.TagMappingModel;
import cn.zh.zhbackend.canseeguan.service.Impl.AlarmEventServiceImpl;
import cn.zh.zhbackend.canseeguan.service.Impl.CellEventServiceImpl;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: hest
 *
 * @create: 2019-10-15 08:41
 **/
public class DataModule {


    public static Map<String,Object> initTagMapping(){
        readExcel readExcel = new readExcel();
        String outmsg="";
        Map<String,Object> outdata = new HashMap<>();
        try {
            Map<Integer, TagMappingModel> integerTagMappingModelMap = readExcel.TagMappingread("TagMapping.xlsx");
            AlarmEventServiceImpl.dicTagDeviceMapping = integerTagMappingModelMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outdata;
    }

    public static Map<String,Object> initCellMapping(){
        readExcel readExcel = new readExcel();
        String outmsg="";
        Map<String,Object> outdata = new HashMap<>();
        try {
            Map<Integer, CellMappingModel> CellMappingModelMap = readExcel.CellMappingread("CellMapping.xlsx");
            CellEventServiceImpl.dicCellMapping = CellMappingModelMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outdata;
    }
}
