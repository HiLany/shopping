package com.lanyang.product.services;





import com.lanyang.product.domain.entity.Product;
import com.shopping.core.dto.PageQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 商品管理服务接口
 * Created by lany on 2018/11/7.
 */
public interface ProductService {

    /**
     * 查看库存
     * @return
     */
    Boolean SearchStorageByProduct(String productName) throws InterruptedException;

    /**
     * 查看商品详细
     * @param productName
     * @return
     */
    String SearchDetailByProduct(String productName) throws InterruptedException;

    /**
     * 根据商品类别查看商品列表
     * @return
     */
    @Transactional(readOnly = true)
    List<Product> searchListByProductType(int productType);

    /**
     * 根据条件对商品进行排序
     * @return
     */
    @Transactional(readOnly = true)
    List<Product> sortListByCondition();

    /**
     * 根据id查询产品
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    Product findOne(String id);

    /**
     * 添加或者更新产品
     * @param product
     * @return
     */
    @Transactional
    Product saveAndUpdateProduct(Product product);

    /**
     * 删除产品
     * @param id
     * @return
     */
    @Transactional
    Boolean deleteProduct(String id);

    /**
     * 根据产品编码查看产品的库存
     * @param productCode
     * @return
     */
    @Transactional
    long checkInventoryByProductCode(String productCode);

    // TODO: 2019/3/29 需要完成分页查询

    /**
     * 分页查询，默认根据product_code升序排列
     * @param page
     * @param size
     * @return
     */
    Page<Product> findProductNoCriteria(Integer page,Integer size);

    Page<Product> findProductNoCriteria(Integer page, Integer size, String sortType, String sortOrder);


    @Transactional(readOnly = true)
    Page<Product> findProductCriteria(Integer page,Integer size,Product product);

    @Transactional(readOnly = true)
    Page<Product> findProductByCondition(Integer page,Integer size,Product product);

    @Transactional(readOnly = true)
    Page<Product> findProductByCondition(PageQueryDto<Product> pageQueryDto) throws Exception;

}
