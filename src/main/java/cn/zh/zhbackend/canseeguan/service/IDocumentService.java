package cn.zh.zhbackend.canseeguan.service;

import cn.zh.zhbackend.canseeguan.domain.BoxModel;
import cn.zh.zhbackend.canseeguan.domain.DocumentModel;
import cn.zh.zhbackend.canseeguan.domain.ListQueryModel;


import java.util.List;
import java.util.Map;

/**
 * ClassName: IDocumentService <br/>
 * Description: <br/>
 * date: 2019/10/18 下午2:05<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
public interface IDocumentService {
    public List<DocumentModel> getDocumentsByBoxId(long id, int pageIndex, int pageItemCount);

}
