package com.lvmen.manager.service;

import com.lvmen.entity.Product;
import com.lvmen.entity.enums.ProductStatus;
import com.lvmen.manager.error.ErrorEnum;
import com.lvmen.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * 产品服务类
 * Created by lvmen on 2019/10/24
 */
@Service
public class ProductService {

    // 用来记录日志
    private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    /**
     * 添加产品
     * @param product
     * @return
     */
    public Product addProduct(Product product){
        // 1. 进行数据校验
        checkProduct(product);
        
        // 2. 设置默认值
        setDefault(product);
        Product result = repository.save(product);
        LOG.debug("创建产品，结果：{}", result);
        return result;
    }

    /**
     * 设置默认值
     * 创建时间、更新时间
     * 投资步长、锁定期、状态
     * @param product
     */
    private void setDefault(Product product) {
        if (product.getCreateAt() == null){
            product.setCreateAt(new Date());
        }
        if (product.getUpdateAt() == null){
            product.setUpdateAt(new Date());
        }
        if (product.getStepAmount() == null){
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getLockTerm() == null){
            product.setLockTerm(0);
        }
        if (product.getStatus() == null){
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 产品数据校验
     * 1. 非空校验
     * 2. 收益率要在0-30以内
     * 3. 投资步长为整数
     * @param product
     */
    private void checkProduct(Product product) {

        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode()); // 抛出一个运行时异常，非法参数异常，异常交由自定义的异常处理器处理
        // @TODO 其他非空校验

        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate()) < 0 && BigDecimal.valueOf(30)
                .compareTo(product.getRewardRate()) >= 0,ErrorEnum.REWARDRATE_ILLEGAL.getCode());

        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue())
                .compareTo(product.getStepAmount()) == 0,ErrorEnum.STEPAMOUNT_ILLEGAL.getCode());

    }


    /**
     * 查询单个商品
     * @param id 商品id
     * @return 返回对应产品或者null
     */
    public Product findOne(String id){
        Assert.notNull(id,"需要产品参数");
        LOG.debug("查询单个产品，id={}", id);

        Product product = repository.findOne(id); //

        LOG.debug("查询单个产品,结果={}", product);

        return product;
    }


    /**
     * 分页查询产品
     * @param idList
     * @param minRewardRate
     * @param maxRewardRate
     * @param statusList
     * @param pageable
     * @return
     */
    public Page<Product> query(List<String> idList,
                               BigDecimal minRewardRate, BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable){
        LOG.debug("查询产品，idList={}，minRewardRate={}，maxRewardRate={},statusList={},pageable={}",
                idList, minRewardRate, maxRewardRate, statusList, pageable);

        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                List<Predicate> predicates = new ArrayList<>();
                if (idList != null && idList.size() > 0){
                    predicates.add(idCol.in(idList));
                }
                if (BigDecimal.ZERO.compareTo(minRewardRate) < 0){
                    predicates.add(cb.ge(rewardRateCol, minRewardRate));
                }
                if (BigDecimal.ZERO.compareTo(minRewardRate) < 0){
                    predicates.add(cb.le(rewardRateCol, maxRewardRate));
                }
                if (statusList != null && statusList.size() > 0){
                    predicates.add(statusCol.in(statusList));
                }

                query.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };
        Page<Product> page = repository.findAll(specification, pageable);

        LOG.debug("查询产品，结果={}",page);
        return page;
    }


}
