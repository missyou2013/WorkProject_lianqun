package activity.lianqun.herry.com.workproject_lianqun.models;

import java.io.Serializable;

/**
 * PROJECT_NAME:WorkProject_lianqun
 * PACKAGE_NAME:activity.lianqun.herry.com.workproject_lianqun.models
 * Description: 摄像头
 * Date: 2016-11-25 15:55
 * User: lxj
 * version V1.0.0
 */

public class ContentSheXiangtou implements Serializable{
    /**
     * name : test
     * key : 7862487229
     * id : 1
     * sn : EE78316303979
     * area : 231
     * pageSum : 0
     * page : false
     * sum : false
     * pageSize : 10
     * pageNum : 1
     * totalCount : 0
     * curDate : 2016-11-25
     * insertDate : 2016-11-24
     * updateDate : null
     * status : 0
     * mtype : 0
     * cname : 青岛连群物业
     * uname : null
     * companyid : 0
     * userid : 0
     */

    private String name;
    private String key;
    private int id;
    private String sn;
    private String area;
    private int pageSum;
    private boolean page;
    private boolean sum;
    private int pageSize;
    private int pageNum;
    private int totalCount;
    private String curDate;
    private String insertDate;
    private Object updateDate;
    private int status;
    private int mtype;
    private String cname;
    private Object uname;
    private int companyid;
    private int userid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public boolean isSum() {
        return sum;
    }

    public void setSum(boolean sum) {
        this.sum = sum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Object getUname() {
        return uname;
    }

    public void setUname(Object uname) {
        this.uname = uname;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
