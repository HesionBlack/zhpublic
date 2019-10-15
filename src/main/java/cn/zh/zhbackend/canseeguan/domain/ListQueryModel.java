package cn.zh.zhbackend.canseeguan.domain;/**
 * ClassName: ListQueryModel <br/>
 * Description: <br/>
 * date: 2019/10/11 下午4:41<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: zhbackend
 *
 * @description:
 *
 * @author: zxb
 *
 * @create: 2019-10-11 16:41
 **/
@Data
@ToString(callSuper = true)
public class ListQueryModel {
    public int pageIndex;
    public int pageItemCount;
    public WhereCondition[] whereConditions;
    public OrderCondition[] orderConditions;
}











