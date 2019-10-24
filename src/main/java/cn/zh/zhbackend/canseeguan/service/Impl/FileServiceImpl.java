package cn.zh.zhbackend.canseeguan.service.Impl;/**
 * ClassName: FileServiceImpl <br/>
 * Description: <br/>
 * date: 2019/10/23 上午8:54<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.Config.YmlConfig;
import cn.zh.zhbackend.canseeguan.Utils.PptUtil;
import cn.zh.zhbackend.canseeguan.dao.FileDao;
import cn.zh.zhbackend.canseeguan.domain.PptActionSignReqModel;
import cn.zh.zhbackend.canseeguan.domain.PptModel;
import cn.zh.zhbackend.canseeguan.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhbackend
 * @description:
 * @author: zxb
 * @create: 2019-10-23 08:54
 **/
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private YmlConfig ymlConfig;

    /**
     * @param edocId
     * @return
     */
    @Override
    public String getViewPdfName(String edocId) {

        String path = "";
        List<Map<String, Object>> filePath = fileDao.getFilePath(edocId);
        for (int i = 0; i < filePath.size(); i++) {
            Map<String, Object> map = filePath.get(i);
            String filepath = map.get("filepath")==null?"":(String) map.get("filepath");
            String savefilename = map.get("savefilename")==null?"":(String) map.get("savefilename");
            String extension = map.get("extension")==null?"":(String) map.get("extension");

            path = filepath + savefilename.replace("." + extension, "_show.pdf");
        }
        return path;
    }

    @Override
    public Map uploadPpt(String fileName, byte[] fileData) throws IOException {
        String rootPath = ymlConfig.getPdfRootPath();
        PptModel pptModel;
        Map<String, Object> map = new HashMap<>();

        return map;
    }

//    @Override
//    public String CheckActionUrl(PptActionSignReqModel srcUrlModel) {
//        return PptUtil;
//    }
}
