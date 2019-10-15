package cn.zh.zhbackend.canseeguan.dao;/**
 * ClassName: AlarmDao <br/>
 * Description: <br/>
 * date: 2019/10/11 下午1:41<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import cn.zh.zhbackend.canseeguan.domain.*;
import com.oracle.tools.packager.Log;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.zh.zhbackend.canseeguan.domain.WhereConditionMethod.*;

/**
 * @program: zhbackend
 * @description:
 * @author: zxb
 * @create: 2019-10-11 13:41
 **/
@Component
public class AlarmDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public ResModel findallAlarm() {
        ResModel resModel = new ResModel();
        List<alarm> alarmRawModelList = mongoTemplate.findAll(alarm.class);
        System.out.println(alarmRawModelList);
        resModel.setData(alarmRawModelList);
        return resModel;
    }

    public ResModel getCurrentAlarmCount() {
        ResModel resModel = new ResModel();

        //构造查询语句
        Query query = new Query(Criteria.where("AlarmState").is("1"));
        //传入mongoTemplate中进行查询
        List<alarm> alarmRawModelList = mongoTemplate.find(query, alarm.class);
        System.out.println("getCurrentAlarmCount:" + alarmRawModelList);
        int size = alarmRawModelList.size();
        System.out.println("getCurrentAlarmCount_list:" + size);
        resModel.setData(size);
        System.out.println("getCurrentAlarmCount：" + resModel);
        return resModel;
    }

    public List<alarm> GetAlarmList(ListQueryModel listQueryModel) {
        ResModel resModel = new ResModel();
        List<alarm> alarmRawModelList = null;
        //从请求的数据中抽取只需要获取一次的数据
        WhereCondition[] whereConditions = listQueryModel.getWhereConditions();
        OrderCondition[] orderConditions = listQueryModel.getOrderConditions();
        int pageIndex = listQueryModel.getPageIndex();
        int pageItemCount = listQueryModel.getPageItemCount();


        if ( null!= whereConditions && whereConditions.length > 0) {
            //查看请求数据中的查询条件
            for (int i = 0; i < whereConditions.length; i++) {
                System.out.println("GetAlarmList_whereConditions:" + whereConditions[i]);
                String field = whereConditions[i].getField();
                System.out.println("GetAlarmList_field："+field);
                if (!field.isEmpty()) {
                    switch (whereConditions[i].getMethod()) {
                        //进行IN查询
                        case IN:
                            System.out.println("已经进入IN");
                            Query query = new Query();
                            String[] condiValue = whereConditions[i].getValue();
                            System.out.println("whereConditions[i].getValue()："+condiValue[0]);
                            query.addCriteria(
                                    new Criteria().orOperator(
                                            Criteria.where(field).in(condiValue[0]),
                                            Criteria.where(field).in(condiValue[1])
                                    ));
                            //查看请求数据中的排序条件
                            if (orderConditions != null && orderConditions.length > 0) {
                                System.out.println("Sort");
                                for (int j = 0; j < orderConditions.length; j++) {
                                    OrderConditionMethod method = orderConditions[i].getMethod();
                                    if (method == OrderConditionMethod.ASC) {
                                        //升序
                                        query.with(Sort.by(
                                                Sort.Order.asc(field))).skip(pageIndex * pageItemCount).limit(pageItemCount);
                                    } else {
                                        // 降序
                                        query.with(Sort.by(
                                                Sort.Order.desc(field))).skip(pageIndex * pageItemCount).limit(pageItemCount);
                                    }
                                }
                            }
                            System.out.println("开始查询");
                            alarmRawModelList = mongoTemplate.find(query, alarm.class);
                            System.out.println("GetAlarmList_IN：" + alarmRawModelList);
//                            resModel.setData(alarmRawModelList);
//                            System.out.println("GetAlarmList_IN：" + resModel);
                            break;
                        case LIKE:
                            break;
                        case EQUAL:
                            break;
                        case LESS_THAN:
                            break;
                        case MORE_THAN:
                            break;
                        case NOT_EQUAL:
                            break;
                        case BTWEEN_LIST:
                            break;
                        case LESS_OR_EQUAL_THAN:
                            break;
                        case MORE_OR_EQUAL_THAN:
                            break;
                        default:

                    }
                }
            }
        }


        return alarmRawModelList;
    }


    public List<tag> GetDevices() {
//        Query query = new Query(Criteria.where("tagId").is(tags));
        return mongoTemplate.findAll(tag.class);
    }
}
