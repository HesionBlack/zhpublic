package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
@Data
@ToString(callSuper = true)
public class DocumentModel extends BoxModel {
    public String documentId;
    public List<EDocumentModel> eDocs;
    public Map<String, Object> docInfo;
}