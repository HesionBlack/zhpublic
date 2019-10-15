package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;


@Data
public class PagedDataModel {
    public int pageIndex;
    public int pageItemCount;
    public int totalCount;
    public Object data;
}