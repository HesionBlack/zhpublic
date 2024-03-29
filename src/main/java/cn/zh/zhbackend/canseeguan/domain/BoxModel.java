package cn.zh.zhbackend.canseeguan.domain;


import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(callSuper = true)
public class BoxModel extends CellModel {
    public Integer boxId;
    public String boxName;
    public int index;
    public double thick;
    public Map<String, Object> boxInfo;
}
