package com.shopping.core.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import com.google.common.base.Strings;
import com.shopping.core.product.domain.dto.ProductDTO;
import io.swagger.annotations.ApiModel;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;


/**
 * 分页查询参数传递
 * Created by lany on 2019/4/11.
 */
@ApiModel("分页查询参数传递")
public class PageQueryDto<T> implements Serializable {

    private static final long serialVersionUID = -2792028968202290118L;

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;
    private String condition;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public <T> T analysisCondition(Class clazz){
        T object = null;
        try {
            object = (T)clazz.newInstance();
            if(Strings.isNullOrEmpty(this.condition)){
                return object;
            }
            Field[] fields = clazz.getDeclaredFields();

            JSONArray json = JSONArray.fromObject(this.condition);
            for(int i=0,len=json.size();i<len;i++){
                JSONObject obj = json.getJSONObject(i);
                for(Field field:fields){
                    if(null != obj.get(field.getName())){
                        Method method = clazz.getDeclaredMethod("set"+ StringUtils.capitalize(field.getName()),field.getType());
                        method.invoke(object,obj.get(field.getName()));
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toString() {
        return "PageQueryDto{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", condition=" + condition +
                '}';
    }

    public static void main(String[] args){
        PageQueryDto<ProductDTO> productDTOPageQueryDto = new PageQueryDto<>();
        String condition = "[{\"productCode\":\"121\",\"productName\":\"2131\"}]";
//        System.out.println(condition);
//        productDTOPageQueryDto.setCondition(condition);
        ProductDTO productDTO = productDTOPageQueryDto.analysisCondition(ProductDTO.class);
        System.out.println(productDTO.getProductName());

//        productDTOPageQueryDto.analysisCondition();
    }
}
