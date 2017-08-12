package com.fh.entity.zxys;

import java.io.Serializable;

/**
 * 中孝医生搜索通用类
 * Created by admin on 2016/11/30.
 */
public class ZxysSearchVO implements Serializable {
    private String token;
    private String condition;   // 搜索条件
    private String area;  // 地区
    private String officeName;   // 科室
    private String consult;   // 咨询方式
    private String orderType;  // 排序方式

    // 分页所需数据
    private int pageIndex;  // 页码
    private int pageSize;   // 页容量
    private int totalCount;     // 数据总数
    private int startIndex;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
//        if(startIndex > totalCount) {
//            // 显示最后5条数据
//            if(totalCount >= pageSize) {
//                startIndex = totalCount - pageSize;
//            }else {
//                startIndex = 0;
//            }
//        }
        return (pageIndex - 1) * pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
