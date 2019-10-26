package com.lvmen.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lvmen.api.ProductRpc;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * Created by lvmen on 2019/10/26
 */
@JsonDeserialize(as = ProductRpcRequest.class) // 将接口反序列化为这个对象
public interface ParamInf {

    public List<String> getIdList();

    public BigDecimal getMinRewardRate();

    public BigDecimal getMaxRewardRate();

    public List<String> getStatusList();


}
