package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class CellModel extends  CabinetModel{
    public String cellId;

    public String cellName;

    public String cellType;

    public Double cellWidth;
}
