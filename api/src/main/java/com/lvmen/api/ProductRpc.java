package com.lvmen.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.lvmen.api.domain.ParamInf;
import com.lvmen.api.domain.ProductRpcRequest;
import com.lvmen.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 产品相关的rpc服务
 * Created by lvmen on 2019/10/26
 */
//@JsonRpcService("rpc/products") // 包含这个注解的就是jsonrpc服务
@JsonRpcService
public interface ProductRpc {

    /**
     * 查询多个产品
     * @param req
     * @return
     */
    List<Product> query(ParamInf req); // 出现反序列化为复杂对象，使用接口

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    Product findOne(String id);
}
