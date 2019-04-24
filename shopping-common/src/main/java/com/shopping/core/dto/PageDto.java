package com.shopping.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lany on 2019/4/23.
 */
public class PageDto<T> implements Serializable {

    private static final long serialVersionUID = -2792028968202290118L;

    private List<T> data = new ArrayList();
    private long total;
    private int totalPage;
    private int currPage;

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public PageDto(List<T> data, long total, int totalPage, int currPage) {
        this.data = data;
        this.total = total;
        this.totalPage = totalPage;
        this.currPage = currPage;
    }

}
