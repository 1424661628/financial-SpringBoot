package com.lvmen.api.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品相关rpc请求对象
 * Created by lvmen on 2019/10/26
 */
public class ProductRpcRequest implements ParamInf{
    private List<String> idList;
    private java.math.BigDecimal minRewardRate;
    private BigDecimal maxRewardRate;
    private List<String> statusList;

//    private Pageable pageable;

    public ProductRpcRequest() {
    }

    public ProductRpcRequest(List<String> idList, BigDecimal minRewardRate, BigDecimal maxRewardRate,
                             List<String> statusList, int page, int pageSize,
                             Sort.Direction direction, String orderBy) {
        this.idList = idList;
        this.minRewardRate = minRewardRate;
        this.maxRewardRate = maxRewardRate;
        this.statusList = statusList;
    }

    @Override
    public String toString() {
        return "ProductRpcRequest{" +
                "idList=" + idList +
                ", minRewardRate=" + minRewardRate +
                ", maxRewardRate=" + maxRewardRate +
                ", statusList=" + statusList +
                '}';
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public BigDecimal getMinRewardRate() {
        return minRewardRate;
    }

    public void setMinRewardRate(BigDecimal minRewardRate) {
        this.minRewardRate = minRewardRate;
    }

    public BigDecimal getMaxRewardRate() {
        return maxRewardRate;
    }

    public void setMaxRewardRate(BigDecimal maxRewardRate) {
        this.maxRewardRate = maxRewardRate;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

}
