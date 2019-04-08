package com.lanyang.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lany on 2019/3/25.
 */
@Entity
@Table(name = "t_product")
@NamedQuery(name="Product.findAll",query = "select p from Product p")
public class Product {

    @Id
    @GeneratedValue(generator = "myGen")
    @GenericGenerator(name = "myGen",strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "proudct_name")
    private String productName;

    @Column(name = "product_type")
    private int productType;

    @Column(name = "pic_addr")
    private String picAddr;

    @Column(name = "inventory")
    private long inventory;

    @Column(name = "price")
    private float price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    public Product(){}

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productType=" + productType +
                ", picAddr='" + picAddr + '\'' +
                ", inventory=" + inventory +
                ", price=" + price +
                '}';
    }
}