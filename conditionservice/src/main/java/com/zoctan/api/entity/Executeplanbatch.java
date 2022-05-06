package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Executeplanbatch {
    /**
     * 执行计划Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 执行计划id
     */
    private Long executeplanid;

    /**
     * 状态，new，waiting，running，pause，finish
     */
    private String batchname;

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
     * 创建者
     */
    private String creator;

    /**
     * 获取执行计划Id
     *
     * @return id - 执行计划Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置执行计划Id
     *
     * @param id 执行计划Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取执行计划id
     *
     * @return executeplanid - 执行计划id
     */
    public Long getExecuteplanid() {
        return executeplanid;
    }

    /**
     * 设置执行计划id
     *
     * @param executeplanid 执行计划id
     */
    public void setExecuteplanid(Long executeplanid) {
        this.executeplanid = executeplanid;
    }

    /**
     * 获取状态，new，waiting，running，pause，finish
     *
     * @return batchname - 状态，new，waiting，running，pause，finish
     */
    public String getBatchname() {
        return batchname;
    }

    /**
     * 设置状态，new，waiting，running，pause，finish
     *
     * @param batchname 状态，new，waiting，running，pause，finish
     */
    public void setBatchname(String batchname) {
        this.batchname = batchname;
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

    /**
     * 获取创建者
     *
     * @return creator - 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
}