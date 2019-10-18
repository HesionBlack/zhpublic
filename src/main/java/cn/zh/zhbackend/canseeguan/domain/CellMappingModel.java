package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CellMappingModel extends CellModel {
        public String cellMapString;
        public double totalBoxThick;
        public int totalBoxCount;
}