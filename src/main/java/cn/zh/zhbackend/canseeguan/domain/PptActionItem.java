package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PptActionItem {
    public String id;

    public PptClickActionType actionType;

    public String actionLink;

    public String rawText;

    public String rawColor;

    public byte cR;

    public byte cG;

    public byte cB;

    public Integer jumpToSlideId;

    public Integer paramFuncMinRefreshTime;

    public Map<String, String> actionParams;
}