package com.lvmen.manager;

import com.lvmen.entity.Product;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Created by lvmen on 2019/10/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @org.junit.Test
    public void testAssertNotNull(){
        Product p = new Product();
        p.setId("null");

        Assert.notNull(p.getId(),"不能为空");


    }



}
