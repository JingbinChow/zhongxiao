package com.fh.entity.zxys;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/29.
 */
public class ZxysDoctorPageVO implements Serializable {

    private String token;   // token
    private int officeId;  // 科室编号
    private String officeName;  //科室
    private String area;    // 地区
    private String consult;  // 服务方式
    private String orderType;  // 排序方式
    private int pageIndex;  // 当前页码
    private int pageSize;   // 页容量
    private int startIndex;   // 起始条数
    private int totalCount;   // 数据总条数

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
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

    public int getStartIndex() {

//        if(startIndex > totalCount) {
//            // 显示最后5条数据
//            if(totalCount >= pageSize) {
//                startIndex = totalCount - pageSize;
//            }else {
//                startIndex = 0;
//            }
//        }
        return (pageIndex -1)*pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
