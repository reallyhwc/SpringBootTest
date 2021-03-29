package com.xuhu.springboodemo.core.vo;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public class BaseVo<T> implements Serializable {
    private static final long serialVersionUID = -1030967086782040198L;

    private List<T> rows;
    //总记录数
    private Integer records;
    //总页数
    private Integer total;
    //当前页码
    private Integer page;

    public BaseVo(){};

    public BaseVo(List<T> list, int records, int page, int pageSize){
        if (pageSize < 1){
            pageSize = 10;
        }
        int del = records / pageSize;
        int mod = records % pageSize;
        Integer total = (mod != 0) ? del + 1 : del;
        this.rows = list;
        this.records = records;
        this.total = total;
        this.page = page;

    }


    public static BaseVo newEmptyInstance(){
        return new BaseVo(Collections.emptyList(),0,1,10);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "BaseVo{" +
                "rows=" + rows +
                ", records=" + records +
                ", total=" + total +
                ", page=" + page +
                '}';
    }
}
