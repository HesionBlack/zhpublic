package cn.zh.zhbackend.canseeguan.dao;

import cn.zh.zhbackend.canseeguan.domain.BoxModel;
import cn.zh.zhbackend.canseeguan.domain.BoxModelS;
import cn.zh.zhbackend.canseeguan.domain.CellMappingModel;
import cn.zh.zhbackend.canseeguan.domain.CellModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CellsqlDao <br/>
 * Description: <br/>
 * date: 2019/10/17 上午11:28<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
@Mapper
public interface CellsqlDao {
    @Select(value = "select * from f_box where f_box.position like '${cell.cellMapString}%'")
    //@Select("SELECT id,boxcode,position,backwidth From f_box WHERE position LIKE CONCAT(#{pos.cellMapString},'%')")
//    @Results(id="BoxMap", value={
//            @Result(column="id", property="boxId", jdbcType= JdbcType.INTEGER, id=true),
//            @Result(column="boxcode", property="boxName", jdbcType=JdbcType.VARCHAR),
//            @Result(column="position", property="index", jdbcType=JdbcType.VARCHAR),
//            @Result(column="backwidth", property="thick", jdbcType=JdbcType.VARCHAR)
//
//    })

//    public List<BoxModelS> getCellBoxes(@Param("cell")CellMappingModel cellMapString);
    public List<Map<String,Object>> getCellBoxes(@Param("cell")CellMappingModel cellMapString);

    @Select("SELECT id,boxcode,position,backwidth From f_box WHERE position LIKE '${cell.cellMapString}%'")
    @Results(id="BoxMap", value={
            @Result(column="id", property="boxId", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="boxcode", property="boxName", jdbcType=JdbcType.VARCHAR),
            @Result(column="position", property="index", jdbcType=JdbcType.VARCHAR),
            @Result(column="backwidth", property="thick", jdbcType=JdbcType.VARCHAR)

    })
    public List<BoxModelS> getCellBoxestoBoxModelS(@Param("cell")CellMappingModel cellMapString);
}
