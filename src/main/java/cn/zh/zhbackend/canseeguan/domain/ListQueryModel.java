package cn.zh.zhbackend.canseeguan.Utils;/**
 * ClassName: ListQueryModel <br/>
 * Description: <br/>
 * date: 2019/10/11 下午4:41<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-11 16:41
 **/
public class ListQueryModel {
    public int pageIndex;
    public int pageItemCount;
    public WhereCondition[] whereConditions;
    public OrderCondition[] orderConditions;

    class WhereCondition
    {
        public String field;
        public WhereConditionMethod method;
        public Object value;
    }

    enum WhereConditionMethod
    {
        EQUAL,
        NOT_EQUAL,
        LIKE,
        LESS_THAN,
        MORE_THAN,
        LESS_OR_EQUAL_THAN,
        MORE_OR_EQUAL_THAN,
        IN,
        BTWEEN_LIST
    }

    class OrderCondition
    {
        public String field;
        public OrderConditionMethod method;
    }

    enum OrderConditionMethod
    {
        ASC,
        DESC
    }

}







