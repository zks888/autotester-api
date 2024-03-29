package com.zks888.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "statics_planandcases")
public class StaticsPlanandcases {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 执行计划id
     */
    private Long testplanid;

    /**
     * 计划名
     */
    private String testplanname;

    /**
     * 成功率
     */
    private Double passrate;

    /**
     * 用例总数
     */
    private Long totalcases;

    /**
     * 用例成功总数
     */
    private Long totalpasscases;

    /**
     * 用例失败总数
     */
    private Long totalfailcases;

    /**
     * 运行时长
     */
    private Long runtime;

    /**
     * 统计日期
     */
    @Column(name = "statics_date")
    private Date staticsDate;

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
     * 获取执行计划id
     *
     * @return testplanid - 执行计划id
     */
    public Long getTestplanid() {
        return testplanid;
    }

    /**
     * 设置执行计划id
     *
     * @param testplanid 执行计划id
     */
    public void setTestplanid(Long testplanid) {
        this.testplanid = testplanid;
    }

    /**
     * 获取计划名
     *
     * @return testplanname - 计划名
     */
    public String getTestplanname() {
        return testplanname;
    }

    /**
     * 设置计划名
     *
     * @param testplanname 计划名
     */
    public void setTestplanname(String testplanname) {
        this.testplanname = testplanname;
    }

    /**
     * 获取成功率
     *
     * @return passrate - 成功率
     */
    public Double getPassrate() {
        return passrate;
    }

    /**
     * 设置成功率
     *
     * @param passrate 成功率
     */
    public void setPassrate(Double passrate) {
        this.passrate = passrate;
    }

    /**
     * 获取用例总数
     *
     * @return totalcases - 用例总数
     */
    public Long getTotalcases() {
        return totalcases;
    }

    /**
     * 设置用例总数
     *
     * @param totalcases 用例总数
     */
    public void setTotalcases(Long totalcases) {
        this.totalcases = totalcases;
    }

    /**
     * 获取用例成功总数
     *
     * @return totalpasscases - 用例成功总数
     */
    public Long getTotalpasscases() {
        return totalpasscases;
    }

    /**
     * 设置用例成功总数
     *
     * @param totalpasscases 用例成功总数
     */
    public void setTotalpasscases(Long totalpasscases) {
        this.totalpasscases = totalpasscases;
    }

    /**
     * 获取用例失败总数
     *
     * @return totalfailcases - 用例失败总数
     */
    public Long getTotalfailcases() {
        return totalfailcases;
    }

    /**
     * 设置用例失败总数
     *
     * @param totalfailcases 用例失败总数
     */
    public void setTotalfailcases(Long totalfailcases) {
        this.totalfailcases = totalfailcases;
    }

    /**
     * 获取运行时长
     *
     * @return runtime - 运行时长
     */
    public Long getRuntime() {
        return runtime;
    }

    /**
     * 设置运行时长
     *
     * @param runtime 运行时长
     */
    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    /**
     * 获取统计日期
     *
     * @return statics_date - 统计日期
     */
    public Date getStaticsDate() {
        return staticsDate;
    }

    /**
     * 设置统计日期
     *
     * @param staticsDate 统计日期
     */
    public void setStaticsDate(Date staticsDate) {
        this.staticsDate = staticsDate;
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
