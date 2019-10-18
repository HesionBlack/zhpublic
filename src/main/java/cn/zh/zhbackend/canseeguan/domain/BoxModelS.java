package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(callSuper = true)
public class BoxModelS extends CellModel {
    public Integer boxId;
    public String boxName;
    public String index;
    public String thick;
}