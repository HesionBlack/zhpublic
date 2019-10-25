package cn.zh.zhbackend.canseeguan.controller;

import cn.zh.zhbackend.canseeguan.Utils.PptUtils;
import cn.zh.zhbackend.canseeguan.domain.PptActionSignReqModel;
import cn.zh.zhbackend.canseeguan.service.Impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhbackend
 * @description: PptController
 * @author: zxb
 * @create: 2019-10-23 10:36
 **/
@RestController
public class PptController {

    @Autowired
    private FileServiceImpl fileService;
    Map<String, Object> map = new HashMap<>();


    /**
     * 上传互动PPT
     *
     * @return
     */
    @PostMapping("/ppt/upload")
    public Map ImportExcel(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        Map map = fileService.uploadPpt(fileName, bytes);
        return this.map;
    }


    /**
     * 功能描述 为ActionURL生成签名
     *
     * @param srcUrlModel
     * @return java.util.Map
     * @author Martin
     * @date 2019/10/24
     */


    @PostMapping("/ppt/sign")
    public Map SignActionUrl(@RequestBody PptActionSignReqModel srcUrlModel) throws Exception {
        String sha1 = PptUtils.SignActionUrl(srcUrlModel.srcUrl);
        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据");
        map.put("data", sha1);
        return map;
    }

    @PostMapping("/ppt/getParamFuncValue")
    public Map GetParamFuncValue(@RequestBody PptActionSignReqModel srcUrlModel) {


        map.put("code", 200);
        map.put("isSuccess", true);
        map.put("message", "成功获取数据");
        map.put("data", 123);
        return map;
    }

}
