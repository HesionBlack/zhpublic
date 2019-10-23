package cn.zh.zhbackend.canseeguan.service.Impl;/**
 * ClassName: FileServiceImpl <br/>
 * Description: <br/>
 * date: 2019/10/23 上午8:54<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.dao.FileDao;
import cn.zh.zhbackend.canseeguan.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-23 08:54
 **/
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;

    /**
     *
     * @param edocId
     * @return
     */
    @Override
    public String getViewPdfName(String edocId) {

        String path = "";
        List<Map<String, Object>> filePath = fileDao.getFilePath(edocId);
        for (int i = 0; i < filePath.size(); i++) {
            Map<String, Object> map = filePath.get(i);
            String filepath = (String) map.get("filepath");
            String savefilename = (String) map.get("savefilename");
            String extension = (String) map.get("extension");

            path = filepath+savefilename.replace("."+extension,"_show.pdf");
        }
        return path;
    }
}
