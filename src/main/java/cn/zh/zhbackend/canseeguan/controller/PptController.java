package cn.zh.zhbackend.canseeguan.controller;

import cn.zh.zhbackend.canseeguan.Utils.PptUtils;
import cn.zh.zhbackend.canseeguan.domain.PptActionSignReqModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PptController {


    Map<String, Object> map = new HashMap<>();
    //为ActionURL生成签名
    @PostMapping("/ppt/sign")
    public Map SignActionUrl(@RequestBody PptActionSignReqModel srcUrlModel) throws Exception {
        String sha1 = PptUtils.SignActionUrl(srcUrlModel.srcUrl);
        map.put("code",200);
        map.put("isSuccess",true);
        map.put("message","成功获取数据");
        map.put("data",sha1);
        return map;
    }

    @PostMapping("/ppt/getParamFuncValue")
    public Map GetParamFuncValue (@RequestBody PptActionSignReqModel srcUrlModel)
    {


        map.put("code",200);
        map.put("isSuccess",true);
        map.put("message","成功获取数据");
        map.put("data",);
        return map;
    }

}
