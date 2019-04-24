package com.lanyang.product.services.Impl;




import com.lanyang.common.Constants;
import com.lanyang.product.domain.entity.Product;
import com.lanyang.product.repositories.ProductRepository;
import com.lanyang.product.services.ProductService;


import com.shopping.core.dto.PageQueryDto;
import com.shopping.utils.TimeUtils;
import com.shopping.utils.TransUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 产品服务实现类
 * Created by lany on 2018/11/7.
 */
@Service
@RefreshScope
public class IProductService implements ProductService{

    private Logger logger = LoggerFactory.getLogger(IProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Value("${deliver.messages:no messages}")
    private String messages;

    @Override
    public Boolean SearchStorageByProduct(String productName)  throws InterruptedException{
        logger.info("SearchStorageByProduct: {}",productName);
        boolean flag = false;
        try {
//            Thread.sleep(2000L);
            int nums = (int)(Math.random()*100);
            if(nums > 40){
                flag = true;
                logger.info("ProductName :"+productName +"is sufficient");
            }else
                logger.info("ProductName :"+productName +"is not enough");
        } catch (Exception e) {
            logger.info("Thread Error");
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String SearchDetailByProduct(String productName) throws InterruptedException{
        Thread.sleep(2000L);
        return "ProductName :"+productName + " Category: Fruit , Description: 'so delicious!'\n"+messages;
    }

    /**
     * 随机生成休眠时间(ms)
     * @return
     */
    public long RandomNum(){
        int sleepTime = (int)(Math.random()*30+31)*100;
        logger.info("SleepTime : {}",sleepTime);
        return sleepTime;
    }

    @Override
    public List<Product> searchListByProductType(int productType) {
        return  productRepository.findByProductType(productType);

    }

    @Override
    public List<Product> sortListByCondition() {
        return null;
    }



    @Override
    public Product saveAndUpdateProduct(Product product) {

        if(product.getProductCode().equals("") || product.getProductCode() == null){
            logger.info("Product is Invalid");
            return null;
        }
        product.setLastUpdateTime(TimeUtils.getCurrentTime());
        return productRepository.saveAndFlush(product);
    }

    //https://stackoverflow.com/questions/49316751/spring-data-jpa-findone-change-to-optional-how-to-use-this/49317013
    @Override
    public Product findOne(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Boolean deleteProduct(String id) {
        Product product_temp = productRepository.getOne(id);
        if(null == product_temp){
            logger.info("Product is not exists");
            return false;
        }
        productRepository.delete(product_temp);
        return true;
    }



    @Override
    public long checkInventoryByProductCode(String productCode) {
        return productRepository.checkInventoryByCode(productCode);
    }

    @Override
    public Page<Product> findProductNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"product_code");
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductNoCriteria(Integer page, Integer size, String sortType, String sortOrder) {
        if(null==sortType || "".equals(sortType)){
            sortType = "product_code";
        }
        Sort.Direction sort;
        //默认情况下按倒序排列
        if(null == sortOrder || Constants.ORDER_DESC.equals(sortOrder) || "".equals(sortOrder)){
             sort = Sort.Direction.DESC;
        }else
            sort = Sort.Direction.ASC;
        Pageable pageable = new PageRequest(page,size,sort,sortType);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductCriteria(Integer page, Integer size, Product product) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"productCode");
        Page<Product> productPage = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("productCode").as(String.class),product.getProductCode());
                Predicate p2 = criteriaBuilder.equal(root.get("productName").as(String.class),product.getProductName());
                Predicate p3 = criteriaBuilder.equal(root.get("productType").as(Integer.class),product.getProductType());
                criteriaQuery.where(criteriaBuilder.and(p1,p2,p3));
                return criteriaQuery.getRestriction();
            }
        },pageable);
        return productPage;
    }

    @Override
    public Page<Product> findProductByCondition(PageQueryDto<Product> pageQueryDto) throws Exception{
        Pageable pageable;
        int page = (pageQueryDto.getCurrent()==0 ||pageQueryDto.getCurrent() ==1)?0:(pageQueryDto.getCurrent()-1);
        Sort.Direction order = Constants.ORDER_ASC.equalsIgnoreCase(pageQueryDto.getSortOrder())? Sort.Direction.ASC: Sort.Direction.DESC;
        if("".equals(pageQueryDto.getSortOrder()) || "".equals(pageQueryDto.getSortField())){
            pageable = new PageRequest(page,pageQueryDto.getPageSize());
        }else
            pageable = new PageRequest(page,pageQueryDto.getPageSize(),order,pageQueryDto.getSortField());
        Page<Product>  productPage = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                Product product = pageQueryDto.analysisCondition(Product.class);
                if(null != product){
                    if(null != product.getProductCode()&&!"".equals(product.getProductCode())){
                        list.add(criteriaBuilder.equal(root.get("productCode").as(String.class),product.getProductCode()));
                    }
                    if(null!= product.getProductName()&&!"".equals(product.getProductName())){
                        list.add(criteriaBuilder.like(root.get("productName").as(String.class),'%'+product.getProductName()+'%'));
                    }
                    if(product.getProductType() != 0){
                        list.add(criteriaBuilder.equal(root.get("productType").as(Integer.class),product.getProductType()));
                    }
                    if(null != product.getLastUpdateTime() && !"".equals(product.getLastUpdateTime())){
                        String[] time = TransUtils.strToArray(product.getLastUpdateTime());
                        if(time.length > 0){
                            list.add(criteriaBuilder.between(root.get("lastUpdateTime").as(String.class),
                                    TimeUtils.transTime(time[0]),
                                    TimeUtils.transTime(time[1])));
                        }
                    }
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return productPage;

    }

    @Override
    public Page<Product> findProductByCondition(Integer page, Integer size, Product product) {
        //根据entity中的属性名称，而不是表中的字段名称进行排序
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"productCode");
        Page<Product> productPage = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
/*                if(null != product){
                    if(null != product.getProductCode()&&!"".equals(product.getProductCode())){
                        list.add(criteriaBuilder.equal(root.get("productCode").as(String.class),product.getProductCode()));
                    }
                    if(null!= product.getProductName()&&!"".equals(product.getProductName())){
                        list.add(criteriaBuilder.like(root.get("productName").as(String.class),product.getProductName()));
                    }
                    if(product.getProductType() != 0){
                        list.add(criteriaBuilder.equal(root.get("productType").as(Integer.class),product.getProductType()));
                    }
                }*/
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return productPage;
    }
}
