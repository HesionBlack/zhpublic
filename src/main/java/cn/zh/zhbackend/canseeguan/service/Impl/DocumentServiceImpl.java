package cn.zh.zhbackend.canseeguan.service.Impl;

import cn.zh.zhbackend.canseeguan.Utils.StringUtils;
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

    Map<String, Object> map = new HashMap<>();

    @Override
    public List<DocumentModel> getDocumentsByBoxId(long id, int pageIndex, int pageItemCount) {
        List<DocumentModel> documents = new ArrayList<>();
        if (!(DataService.dicCellMapping.isEmpty())) {
            documents = new ArrayList<>();
            //从数据库中查询数据
            List<Map<String, Object>> documentsByBoxId = documentSqlDao.getDocumentsByBoxId(id, pageIndex, pageItemCount);
            //查出数据进行相应的操作,遍历结果集
            for (int i = 0; i < documentsByBoxId.size(); i++) {
                Map<String, Object> map = documentsByBoxId.get(i);
                DocumentModel document = new DocumentModel();
                document.documentId = StringUtils.longToString(map.get("id"));
                document.docInfo = map;
                documents.add(document);
            }
            //定义一个电子文档集合
            List<EDocumentModel> eDocuments = new ArrayList<>();
            //根据结果集的数据，再次查询数据库
            if (documents.size() > 0) {
                String sql = "";
                //组装条件sql语句
                for (int i = 0; i < documents.size(); i++) {
                    DocumentModel documentModel = documents.get(i);
                    sql = sql + documentModel.documentId + ",";
                }
                sql = sql.substring(0, sql.length() - 1);
                //向数据库查询数据
                List<Map<String, Object>> documentsBydocId = documentSqlDao.getDocumentsBydocId(sql);

                //遍历结果
                for (int i = 0; i < documentsBydocId.size(); i++) {
                    Map<String, Object> map = documentsBydocId.get(i);
                    EDocumentModel eDocument = new EDocumentModel();
                    eDocument.eDocId = StringUtils.longToString(map.get("id"));
                    eDocument.eDocName = map.get("title").toString();
                    eDocument.src = map.get("filepath").toString();
                    eDocument.type = EDocumentType.PDF;
                    eDocument.eDocInfo = map;
                    //将电子文档存入集合中
                    eDocuments.add(eDocument);
                }
                //存在电子文档
                if (eDocuments.size()>0){
                    //和文档集合里的数据通过documentId匹配上，将电子文档的信息存入document的edocs属性中
                    List<EDocumentModel> docEdocs = new ArrayList<>();
                    for (DocumentModel documentModel :
                            documents) {
                        for (EDocumentModel eDocumentModel : eDocuments) {
                            String archid = StringUtils.longToString(eDocumentModel.eDocInfo.get("archid"));
                            if(archid.equals(documentModel.documentId)){
                                docEdocs.add(eDocumentModel);
                            }
                            if (docEdocs.size() > 0)
                            {
                                documentModel.eDocs = docEdocs;
                            }
                        }
                    }
                }
            }
        }
        return documents;
    }


}
