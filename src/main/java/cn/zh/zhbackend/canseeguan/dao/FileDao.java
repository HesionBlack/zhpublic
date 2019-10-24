package cn.zh.zhbackend.canseeguan.dao;/**
 * ClassName: FileDao <br/>
 * Description: <br/>
 * date: 2019/10/23 上午8:55<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
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
 * @create: 2019-10-23 08:55
 **/
@Mapper
@Repository
public interface FileDao {
    @Select("SELECT filepath,savefilename,extension FROM e_record WHERE id = #{eDocId}")
    public List<Map<String,Object>> getFilePath(String eDocId);


}
