package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import org.bson.BsonElement;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
public class Tag {
    @BsonId
    public ObjectId _id;
    public String tagId;//变量Id

    public String val;//变量值

    public Long time;//时间
}