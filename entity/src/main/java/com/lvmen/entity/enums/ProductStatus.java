package com.lvmen.entity.enums;

/**
 * 产品状态
 * Created by lvmen on 2019/10/24
 */
public enum ProductStatus {
    AUDITING("审核中"),
    IN_SELL("销售中"),
    LOCKED("暂停销售"),
    FINISHED("已结束");

    private String desc;

    ProductStatus(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}
