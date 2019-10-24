package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class PptModel {
    public String fileName;

    public String key;

    public int slideCount;

    public PptSlideModel[] slides;
}