package cn.zh.zhbackend.canseeguan.dao;

import cn.zh.zhbackend.canseeguan.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DocumentSqlDao <br/>
 * Description: <br/>
 * date: 2019/10/18 下午2:01<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
@Mapper
public interface DocumentSqlDao {

    @Select("select * from f_document where boxid=#{id} order by doccode limit #{pageIndex},#{pageItemCount}")
    public List<Map<String, Object>> getDocumentsByBoxId(long id, int pageIndex, int pageItemCount);

    @Select("SELECT * From e_record WHERE archid IN(${sql})")
    public List<Map<String, Object>> getDocumentsBydocId(String sql);
}
