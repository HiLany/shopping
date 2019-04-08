//package com.shopping.core.product.repository;
//
//import com.shopping.core.product.domain.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * Created by lany on 2019/3/27.
// */
//@Repository
//public interface ProductRepository extends JpaRepository<Product,String> {
//
//    @Query(value = "select inventory from t_product where productCode = ?1 ",nativeQuery = true)
//    long checkInventoryByCode(String productCode);
//
//    List<Product> findByProductType(int productType);
//
//    Product findByProductCode(String productCode);
//
//}