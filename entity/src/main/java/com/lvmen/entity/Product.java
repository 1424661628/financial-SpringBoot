package com.lvmen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品
 */
@Entity
//@ApiModel(value = "",description = "")
public class Product {
    @Id
    private String id;
    private String name;
    /**
     * @see com.lvmen.entity.enums.ProductStatus
     */
    @ApiModelProperty(value = "状态", dataType = "com.lvmen.entity.enums.ProductStatus")
    private String status;
    // 起投金额
    private BigDecimal thresholdAmount;
    // 投资步长
    private BigDecimal stepAmount;
    // 锁定期
    private Integer lockTerm;
    // 收益率，主要与其他数据相乘，所以使用BigDecimal
    private BigDecimal rewardRate;
    private String memo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // SpringBoot默认反序列化的时间格式'yyyy-MM-dd'T'HH:mm:ss.SSS'
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;
    private String createUser;
    private String updateUser;

    public Product() {
    }

    public Product(String id, String name, String status, BigDecimal thresholdAmount, BigDecimal stepAmount, BigDecimal rewardRate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.thresholdAmount = thresholdAmount;
        this.stepAmount = stepAmount;
        this.rewardRate = rewardRate;
    }

    public String toString(){ // 使用commons-lang3的反射 重写toString()
        return ReflectionToStringBuilder.toString(this);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public BigDecimal getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(BigDecimal stepAmount) {
        this.stepAmount = stepAmount;
    }

    public Integer getLockTerm() {
        return lockTerm;
    }

    public void setLockTerm(Integer lockTerm) {
        this.lockTerm = lockTerm;
    }

    public BigDecimal getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(BigDecimal rewardRate) {
        this.rewardRate = rewardRate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
