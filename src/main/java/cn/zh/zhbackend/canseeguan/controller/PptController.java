package cn.zh.zhbackend.canseeguan.controller;/**
 * ClassName: PptController <br/>
 * Description: <br/>
 * date: 2019/10/25 下午2:33<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.domain.ListQueryModel;
import cn.zh.zhbackend.canseeguan.domain.PagedDataModel;
import cn.zh.zhbackend.canseeguan.service.Impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: hesion
 *
 * @create: 2019-10-25 14:33
 **/
public class PptController {

    @Autowired
    private FileServiceImpl fileService;

   /**
       *@Author hst 
       *@Description //TODO 获取PPT动态参数方法列表
       *@Date 下午2:36 2019/10/25
       *@Param [query] 
       * @return java.util.Map 
       **/
    @PostMapping("/ppt/getParamFuncList")
    public Map GetParamFuncList(@RequestBody ListQueryModel query){
        Map<String,Object> map = new HashMap<>();
        PagedDataModel pagedDataModel=new PagedDataModel();

        map.put("code",200);
        fileService.GetParamFuncList(query);
        pagedDataModel.setPageIndex(query.pageIndex);
        pagedDataModel.setPageItemCount(query.pageItemCount);

//        pagedDataModel.setTotalCount();
//        pagedDataModel.setData();
        map.put("isSuccess",true);
        map.put("data",2);
        map.put("message","Success");
        return map;
    }

    

}
