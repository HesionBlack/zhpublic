package cn.zh.zhbackend.canseeguan.Config;/**
 * ClassName: ymlConfig <br/>
 * Description: <br/>
 * date: 2019/10/23 上午9:44<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-23 09:44
 **/
@Data
@Component
public class YmlConfig {
    @Value("${pdfRootPath}")
    private String pdfRootPath;
}
