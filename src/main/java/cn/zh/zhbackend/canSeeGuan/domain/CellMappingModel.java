package cn.zh.zhbackend.canSeeGuan.domain;

import lombok.Data;

@Data
public class CellMappingModel extends CellModel {
        public String cellMapString;
        public double totalBoxThick;
        public int totalBoxCount;
}