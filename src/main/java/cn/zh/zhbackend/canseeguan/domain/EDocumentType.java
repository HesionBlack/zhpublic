package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

public enum EDocumentType {
    //PDF枚举类型
    PDF(0);

    private final int value;
    private EDocumentType(int value) {
        this.value = value;
    }

    public EDocumentType valueOf(int value) {
        switch (value) {
            case 0:
                return EDocumentType.PDF;
            default:
                return null;
        }
    }
}