package cn.zh.zhbackend.canseeguan.service.Impl;

import cn.zh.zhbackend.canseeguan.dao.DocumentSqlDao;
import cn.zh.zhbackend.canseeguan.domain.*;
import cn.zh.zhbackend.canseeguan.service.DataModule;
import cn.zh.zhbackend.canseeguan.service.DataService;
import cn.zh.zhbackend.canseeguan.service.IDocumentService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service
public class DocumentServiceImpl implements IDocumentService {
    @Autowired
    private DocumentSqlDao documentSqlDao;

    Map<String,Object> map = new HashMap<>();
    @Override
    public Map getDocumentsByBoxId(long id,int pageIndex,int pageItemCount) {
        if(!(DataService.dicCellMapping.isEmpty())) {
            List<DocumentModel> documents = new ArrayList<>();
            //从数据库中查询数据
            List<Map<String, Object>> documentsByBoxId = documentSqlDao.getDocumentsByBoxId(id, pageIndex, pageItemCount);
            //查出数据进行相应的操作
            for (int i = 0; i < documentsByBoxId.size(); i++) {
                System.out.println(documentsByBoxId.get(i));
            }
            System.out.println(documentsByBoxId);
        }
        return map;
    }


}
