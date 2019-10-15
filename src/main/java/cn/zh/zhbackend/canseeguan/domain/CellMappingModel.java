package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class CellMappingModel extends CellModel {
        public String cellMapString;
        public double totalBoxThick;
        public int totalBoxCount;
}