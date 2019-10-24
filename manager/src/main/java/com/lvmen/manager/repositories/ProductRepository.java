package com.lvmen.manager.repositories;

import com.lvmen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 产品管理
 * Created by lvmen on 2019/10/24
 */
public interface ProductRepository extends CrudRepository<Product, String>, JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

}
