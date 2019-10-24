package cn.zh.zhbackend.canseeguan.controller;/**
 * ClassName: PptController <br/>
 * Description: <br/>
 * date: 2019/10/23 上午10:36<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.dao.FileDao;
import cn.zh.zhbackend.canseeguan.domain.PptActionSignReqModel;
import cn.zh.zhbackend.canseeguan.service.Impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhbackend
 * @description: PptController
 * @author: zxb
 * @create: 2019-10-23 10:36
 **/
public class PptController {

    @Autowired
    private FileServiceImpl fileService;

    Map<String, Object> map = new HashMap<>();


    /**
     * 上传互动PPT
     * @return
     */
    @PostMapping("/ppt/upload")
    public Map ImportExcel(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        Map map = fileService.uploadPpt(fileName,bytes);
        return this.map;
    }

    @PostMapping("/ppt/checkActionUrl")
    public Map CheckActionUrl(PptActionSignReqModel srcUrlModel){
        map.put("code",200);
        fileService.CheckActionUrl(srcUrlModel.srcUrl);
        map.put("isSuccess",true);
        map.put("data",2);
        map.put("msg","testing");

        return map;
    }

}
