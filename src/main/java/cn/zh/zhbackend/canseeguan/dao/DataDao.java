package cn.zh.zhbackend.canseeguan.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Repository
public interface DataDao {


    /**
     * 根据id查询文件是否存在pdf
     * @param eDocId
     * @return
     */
    @Select("SELECT filepath,savefilename,extension FROM e_record WHERE id = #{id};")
    public  List<Map<String,Object>>  getFilePath(String eDocId);
}