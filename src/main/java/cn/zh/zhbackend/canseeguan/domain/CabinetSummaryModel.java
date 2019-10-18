package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CabinetSummaryModel extends  CabinetModel {
        public double totalThick;
        public double totalWidth;
        public int totalBoxCount;
    }