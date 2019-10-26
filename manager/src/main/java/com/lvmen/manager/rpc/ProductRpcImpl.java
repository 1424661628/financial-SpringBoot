package com.lvmen.manager.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.lvmen.api.ProductRpc;
import com.lvmen.api.domain.ParamInf;
import com.lvmen.api.domain.ProductRpcRequest;
import com.lvmen.entity.Product;
import com.lvmen.manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * rpc服务实现类
 * Created by lvmen on 2019/10/26
 */
@AutoJsonRpcServiceImpl // 自动JsonRpc实现类，实现对应的rpcAPI
@Component
public class ProductRpcImpl implements ProductRpc {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpc.class);
    @Autowired
    private ProductService productService;

    @Override
    public List<Product> query(ParamInf req) {
        LOG.info("查询多个产品，请求：{}", req);
        // jsonRPC 不能传递复杂类型
        Pageable pageable = new PageRequest(0, 1000, Sort.Direction.DESC, "rewardRate");
        Page<Product> result = productService.query(req.getIdList(), req.getMinRewardRate(),
                req.getMaxRewardRate(), req.getStatusList(), pageable);
        LOG.info("查询多个产品，结果：{}",result);
        return result.getContent();
    }

    @Override
    public Product findOne(String id) {
        LOG.info("查询商品详情，请求：{}",id);
        Product result = productService.findOne(id);
        LOG.info("查询商品详情，结果：{}",result);
        return result;
    }
}
