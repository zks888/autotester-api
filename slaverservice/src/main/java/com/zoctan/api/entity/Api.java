package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Api {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * DeployUnitId
     */
    private Long deployunitid;

    /**
     * 发布单元名
     */
    private String deployunitname;

    /**
     * 接口名
     */
    private String apiname;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    private String creator;

    /**
     * 访问方式，字典表获取
     */
    private String visittype;

    public String getApistyle() {
        return apistyle;
    }

    public void setApistyle(String apistyle) {
        this.apistyle = apistyle;
    }

    private String apistyle;

    /**
     * url访问路径
     */
    private String path;

    public String getRequestcontenttype() {
        return requestcontenttype;
    }

    public void setRequestcontenttype(String requestcontenttype) {
        this.requestcontenttype = requestcontenttype;
    }

    private String requestcontenttype;

    public String getResponecontenttype() {
        return responecontenttype;
    }

    public void setResponecontenttype(String responecontenttype) {
        this.responecontenttype = responecontenttype;
    }

    private String responecontenttype;



    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上一次修改时间
     */
    @Column(name = "lastmodify_time")
    private Date lastmodifyTime;

    /**
     * 获取Id
     *
     * @return id - Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置Id
     *
     * @param id Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取DeployUnitId
     *
     * @return deployunitid - DeployUnitId
     */
    public Long getDeployunitid() {
        return deployunitid;
    }

    /**
     * 设置DeployUnitId
     *
     * @param deployunitid DeployUnitId
     */
    public void setDeployunitid(Long deployunitid) {
        this.deployunitid = deployunitid;
    }

    /**
     * 获取发布单元名
     *
     * @return deployunitname - 发布单元名
     */
    public String getDeployunitname() {
        return deployunitname;
    }

    /**
     * 设置发布单元名
     *
     * @param deployunitname 发布单元名
     */
    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    /**
     * 获取接口名
     *
     * @return apiname - 接口名
     */
    public String getApiname() {
        return apiname;
    }

    /**
     * 设置接口名
     *
     * @param apiname 接口名
     */
    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    /**
     * 获取访问方式，字典表获取
     *
     * @return visittype - 访问方式，字典表获取
     */
    public String getVisittype() {
        return visittype;
    }

    /**
     * 设置访问方式，字典表获取
     *
     * @param visittype 访问方式，字典表获取
     */
    public void setVisittype(String visittype) {
        this.visittype = visittype;
    }

    /**
     * 获取url访问路径
     *
     * @return path - url访问路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置url访问路径
     *
     * @param path url访问路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取上一次修改时间
     *
     * @return lastmodify_time - 上一次修改时间
     */
    public Date getLastmodifyTime() {
        return lastmodifyTime;
    }

    /**
     * 设置上一次修改时间
     *
     * @param lastmodifyTime 上一次修改时间
     */
    public void setLastmodifyTime(Date lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }
}