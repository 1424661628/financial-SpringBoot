package com.lvmen.manager.controller;

import com.lvmen.entity.Product;
import com.lvmen.manager.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lvmen on 2019/10/24
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static Logger LOG= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        LOG.info("创建产品，参数：{}", product);

        Product result = service.addProduct(product);

        LOG.info("创建产品，结果是：{}", result);
        return result;
    }

    @RequestMapping(value = "/{id}")
    public Product findOne(@PathVariable String id){
        LOG.info("查询单个产品,id={}",id);

        Product product = service.findOne(id);

        LOG.info("查询单个产品结果", product);
        return product;
    }

    @GetMapping
    public Page<Product> query(String ids, BigDecimal minRewardRate, BigDecimal maxRewardRate, String status,
                               @RequestParam(defaultValue = "0") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize){
        LOG.info("查询产品，ids={}，minRewardRate={}，maxRewardRate={}，status={}, pageNum={}, pageSize={}");

        List<String> idList = null, statusList = null;
        if (!StringUtils.isEmpty(ids)) {
            idList = Arrays.asList(ids.split(","));
        }
        if (!StringUtils.isEmpty(status)) {
            statusList = Arrays.asList(status.split(","));
        }

        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<Product> page = service.query(idList, minRewardRate, maxRewardRate, statusList, pageable);

        LOG.info("查询产品结果", page);
        return page;
    }

}
