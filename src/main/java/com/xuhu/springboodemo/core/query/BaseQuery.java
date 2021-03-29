package com.xuhu.springboodemo.core.query;


import java.io.Serializable;

public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 9074173734251712098L;

    /**
     * 页码
     */
    private Integer page = 1;
    /**
     * 每页显示条数
     */
    private Integer rows = 10;

    private Integer offset = 0;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void validatePage(){
        if ( page == null || rows == null){
            throw new RuntimeException("分页参数不能为空");
        }
        if( rows >500){
            throw new RuntimeException("一次参数条数过多");
        }
        if(page <=1){
            page = 1;
        }
        if(rows<1){
            rows = 10;
        }
        offset = (page-1)*rows;
    }
}
