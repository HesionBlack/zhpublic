package cn.zh.zhbackend.canseeguan.controller;

import cn.zh.zhbackend.canseeguan.Config.YmlConfig;
import cn.zh.zhbackend.canseeguan.Utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

@Controller
public class FileController {
    @Autowired
    private  YmlConfig ymlConfig;
    //跳转到上传文件的页面
    @RequestMapping(value = "/gouploadimg", method = RequestMethod.GET)
    public String goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        return "upload";
    }

    //处理文件上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();  //获取上传文件原名
        System.out.println("fileName-->" + fileName);

        System.out.println("getContentType-->" + contentType);
        String filePath = ymlConfig.getPdfRootPath()+"uploads/company1/PPT";
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败");
            return  "上传失败";
        }
        //返回json
        return  "上传成功";
    }


}