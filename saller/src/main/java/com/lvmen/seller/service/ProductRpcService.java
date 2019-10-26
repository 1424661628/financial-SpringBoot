package com.lvmen.seller.service;

import com.lvmen.api.ProductRpc;
import com.lvmen.api.domain.ProductRpcRequest;
import com.lvmen.entity.Product;
import com.lvmen.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品相关服务
 * Created by lvmen on 2019/10/26
 */
@Service
public class ProductRpcService {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    @Autowired
    private ProductRpc productRpc;

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        ProductRpcRequest req = new ProductRpcRequest();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());
        Pageable pageable = new PageRequest(0, 1000, Sort.Direction.DESC, "rewardRate");
        req.setStatusList(status);

        LOG.info("rpc查询全部产品，请求：{}", req);
        List<Product> result = productRpc.query(req); // 使用rpc查询
        LOG.info("rpc查询全部产品，结果：{}",result);
        return result;
    }

    public Product findOne(String id){
        LOG.info("查询单个产品，请求id={}", id);
        Product result = productRpc.findOne(id); // 使用rpc查询
        LOG.info("查询单个产品，结果：{}",result);
        return result;
    }

    @PostConstruct // 测试方法
    public void testFindAll(){
        System.out.println("查询全部产品");
        findAll();
//        System.out.println("查询单个产品");
//        findOne("001");
    }
}
