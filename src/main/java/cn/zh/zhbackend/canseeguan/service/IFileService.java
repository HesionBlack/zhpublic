package cn.zh.zhbackend.canseeguan.service;/**
 * ClassName: IFileService <br/>
 * Description: <br/>
 * date: 2019/10/23 上午8:49<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.domain.ListQueryModel;
import cn.zh.zhbackend.canseeguan.domain.PptActionSignReqModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Map;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-23 08:49
 **/
public interface IFileService {
    /**
        *@Author hst
        *@Description //TODO 获取PDF文件名称
        *@Date 下午2:41 2019/10/25
        *@Param [edocId]
        * @return java.lang.String
        **/
    String getViewPdfName(String edocId);
    /**
        *@Author hst 
        *@Description //TODO 上传PPT
        *@Date 下午2:40 2019/10/25
        *@Param [fileName, fileData] 
        * @return java.util.Map 
        **/
    Map uploadPpt(String fileName, byte[] fileData) throws IOException;

    void GetParamFuncList(ListQueryModel query);
}
