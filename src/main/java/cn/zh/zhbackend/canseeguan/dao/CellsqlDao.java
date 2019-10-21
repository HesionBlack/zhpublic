package cn.zh.zhbackend.canseeguan.dao;

import cn.zh.zhbackend.canseeguan.domain.*;
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
    public List<Map<String, Object>> getCellBoxes(@Param("cell") CellMappingModel cellMapString);

    @Select("SELECT id,boxcode,position,backwidth From f_box WHERE position LIKE '${cell.cellMapString}%'")
    @Results(id = "BoxMap", value = {
            @Result(column = "id", property = "boxId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "boxcode", property = "boxName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "position", property = "index", jdbcType = JdbcType.VARCHAR),
            @Result(column = "backwidth", property = "thick", jdbcType = JdbcType.VARCHAR)

    })
    public List<BoxModelS> getCellBoxestoBoxModelS(@Param("cell") CellMappingModel cellMapString);

    @Select("select SQL_CALC_FOUND_ROWS * from f_document where fondscode like '%${queryString}%'"
            + "or archcode like '%${queryString}%'"
            + "or author like '%${queryString}%' "
            + "or box like '%${queryString}%'"
            + "or classfy like '%${queryString}%'"
            + "or classfyname like '%${queryString}%'"
            + "or retentionperiod like '%${queryString}%'"
            + "or boxid like '%${queryString}%'"
            + "or department like '%${queryString}%'"
            + "or security like '%${queryString}%'"
            + "or departmentsn like '%${queryString}%'"
            + "or title like '%${queryString}%'"
            + "or filetitle like '%${queryString}%'"
            + "or filetype like '%${queryString}%'"
            + "or status like '%${queryString}%'"
            + "limit #{itemStart}, #{pageItemCount}")
    public List<Map<String, Object>> getQueryDocuments(String queryString, int pageIndex, int pageItemCount, int itemStart);
    @Select("SELECT * From f_box WHERE id IN(${quertString})")
    public List<Map<String,Object>>  getBoxInfoById(String quertString);


    @Select("select SQL_CALC_FOUND_ROWS *,substring_index(`position`, '-', 1) cellMapString "
            + "from f_box where fondsid like '%${queryString}%' "
            +"or boxcode like '%${queryString}%'"
            +"or classfy like '%${queryString}%'"
            +"or classfyname like '%${queryString}%'"
            +"or memo like '%${queryString}%'"
            +"or department like '%${queryString}%'"
            +"limit #{pageIndex},#{pageItemCount}"
    )
    List<Map<String,Object>> getQueryBoxesInfo(String queryString, int pageIndex, int pageItemCount, int itemStart);

    @Select("SELECT substring_index(`position`, '-', 1) cellMapString," +
            "count(*) totalBoxes," +
            "round(sum(`backwidth`) / 1000 , 3) totalThick " +
            "FROM f_box GROUP BY cellMapString")
    List<Map<String,Object>> UpdateCellTickCache();
}
