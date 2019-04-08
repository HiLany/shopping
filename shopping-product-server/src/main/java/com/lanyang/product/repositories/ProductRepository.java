package com.lanyang.product.repositories;


import com.lanyang.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lany on 2019/3/25.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,String>,JpaSpecificationExecutor<Product> {

    @Query(value = "select inventory from t_product where product_code = ?1 ",nativeQuery = true)
    long checkInventoryByCode(String productCode);

    List<Product> findByProductType(int productType);

    Product findByProductCode(String productCode);

}