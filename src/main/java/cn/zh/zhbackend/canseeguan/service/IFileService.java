package cn.zh.zhbackend.canseeguan.service;/**
 * ClassName: IFileService <br/>
 * Description: <br/>
 * date: 2019/10/23 上午8:49<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

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
    public String getViewPdfName(String edocId);
    Map uploadPpt(String fileName, byte[] fileData) throws IOException;
//    String CheckActionUrl(String srcUrlWithSign);
}
