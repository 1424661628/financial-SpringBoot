package com.lvmen.manager.controller;

import com.lvmen.entity.Product;
import com.lvmen.entity.enums.ProductStatus;
import com.lvmen.manager.repositories.ProductRepository;
import com.lvmen.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvmen on 2019/10/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 随机端口
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;
    @Autowired
    private ProductRepository productRepository;

    // 正常产品数据
    private static List<Product> normals = new ArrayList<>();
    // 异常产品数据
    private static List<Product> exceptions = new ArrayList<>();



    @BeforeClass
    public static void init(){
        Product p1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "活期盈-金色人生", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "朝朝盈-聚财", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));

        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

        Product e1 = new Product(null, "编号不可为空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.34));
        Product e2 = new Product("E002", "收益率范围错误", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "投资步长需为整数", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.44));
        // @TODO 我们只测试了一部分，还要测试边界条件
        exceptions.add(e1);
        exceptions.add(e2);
        exceptions.add(e3);

        // 对Rest添加一个自定义的异常处理
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            }
        };
        rest.setErrorHandler(errorHandler);

    }

    /**
     * 添加一个产品
     */
    @Test          // 方式二
    @Transactional // 只有直接使用repository操作数据库才可以回滚，http方式不能
    public void create(){
        normals.forEach(product -> {
            Product result = RestUtil.postJSON(rest, baseUrl+"/products", product, Product.class);
            Assert.notNull(result.getCreateAt(), "插入失败");
        });
    }

    /**
     * 添加一个非法产品
     */
    @Test
    public void createException(){
        exceptions.forEach(product -> {
            Map<String, String> result = RestUtil.postJSON(rest, baseUrl+"/products", product, HashMap.class);
            Assert.isTrue(result.get("message").equals(product.getName()), "插入成功"); // 我们把抛出的异常的信息和产品的名称对应起来。验证指定的错误
        });
    }

    /**
     * 查询单个产品
     */
    @Test
    public void findOne(){
        normals.forEach(product -> {
            Product result = rest.getForObject(baseUrl + "/products/" + product.getId(), Product.class);
            Assert.isTrue(result.getId().equals(product.getId()), "查询失败");
        });
        exceptions.forEach(product -> {
            Product result = rest.getForObject(baseUrl + "/products/" + product.getId(), Product.class);
            Assert.isNull(result,"查询失败");
        });
    }


    /**
     * 方式一：
     * 事务控制测试类
     * 添加的垃圾数据
     */
    @Test
    @Transactional
    public void transaction(){
        normals.forEach(product -> {
            product.setLockTerm(0);
            productRepository.saveAndFlush(product);
        });
    }


    /**
     * 方式二：
     * 清除添加的数据
     */
    @Test
    public void clean(){
        productRepository.delete(normals);
    }

    /**
     * 方式三：
     * 使用内存数据库h2
     */
}
