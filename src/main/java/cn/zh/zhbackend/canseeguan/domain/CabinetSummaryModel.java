package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class CabinetSummaryModel extends  CabinetModel {
        public double totalThick;
        public double totalWidth;
        public int totalBoxCount;
    }