package cn.zh.zhbackend.canseeguan.dao;

import cn.zh.zhbackend.canseeguan.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DocumentSqlDao <br/>
 * Description: <br/>
 * date: 2019/10/18 下午2:01<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
@Mapper
public class DocumentSqlDao {
    public DocumentModel[] getDocumentsByBoxId(ListQueryModel listQueryModel) {
        ResModel resModel = new ResModel();
        List<Alarm> alarmRawModelList = null;
        //从请求的数据中抽取只需要获取一次的数据
        WhereCondition[] whereConditions = listQueryModel.getWhereConditions();
        OrderCondition[] orderConditions = listQueryModel.getOrderConditions();
        int pageIndex = listQueryModel.getPageIndex();
        int pageItemCount = listQueryModel.getPageItemCount();


        if ( null!= whereConditions && whereConditions.length > 0) {
            //查看请求数据中的查询条件
            for (int i = 0; i < whereConditions.length; i++) {
                System.out.println("GetAlarmList_whereConditions:" + whereConditions[i]);
                Object field = whereConditions[i].getField();
                System.out.println("GetAlarmList_field："+field);
                if (!"null".equals(field)) {
                    switch (whereConditions[i].getMethod()) {
                        //进行IN查询
                        case IN:
                            System.out.println("已经进入IN");
                            Query query = new Query();
                            ArrayList<String> arrayList= (ArrayList<String>) whereConditions[i].getValue();
                            String[] condiValue = arrayList.toArray(new String[2]);
                            query.addCriteria(
                                    new Criteria().orOperator(
                                            Criteria.where(field.toString()).in(condiValue[0]),
                                            Criteria.where(field.toString()).in(condiValue[1])
                                    ));
                            //查看请求数据中的排序条件
                            if (orderConditions != null && orderConditions.length > 0) {
                                System.out.println("Sort");
                                for (int j = 0; j < orderConditions.length; j++) {
                                    OrderConditionMethod method = orderConditions[i].getMethod();
                                    if (method == OrderConditionMethod.ASC) {
                                        //升序
                                        query.with(Sort.by(
                                                Sort.Order.asc(field.toString()))).skip(pageIndex * pageItemCount).limit(pageItemCount);
                                    } else {
                                        // 降序
                                        query.with(Sort.by(
                                                Sort.Order.desc(field.toString()))).skip(pageIndex * pageItemCount).limit(pageItemCount);
                                    }
                                }
                            }
                            System.out.println("开始查询");
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
}
