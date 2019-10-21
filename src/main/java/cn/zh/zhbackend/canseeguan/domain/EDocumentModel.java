package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

import java.util.Map;

@Data
public class EDocumentModel {
    public String eDocId;
    public String eDocName;
    public EDocumentType type;
    public String src;
    public Map<String, String> eDocInfo;
}