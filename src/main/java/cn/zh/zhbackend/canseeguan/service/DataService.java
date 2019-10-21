package cn.zh.zhbackend.canseeguan.service;/**
 * ClassName: DataModule <br/>
 * Description: <br/>
 * date: 2019/10/15 上午8:38<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.domain.CellMappingModel;
import cn.zh.zhbackend.canseeguan.domain.TagMappingModel;

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
    public static TagMappingModel[] tagMappingModels;

    public static Map<Integer, TagMappingModel> dicTagDeviceMapping;
    public static Map<Integer, CellMappingModel> dicCellMapping;
}
