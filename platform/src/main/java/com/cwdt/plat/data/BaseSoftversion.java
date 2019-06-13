package com.cwdt.plat.data;

import com.cwdt.plat.dataopt.BaseSerializableData;

import java.io.Serializable;
import java.util.Date;

public class BaseSoftversion extends BaseSerializableData {

    //
    private Integer id;
    //软件类型 根据软件类型进行更新 如内部端 公众端
    private Integer softid;
    //组织机构ID 可以用来根据组织机构的不同进行分别更新
    private Integer orgid;
    //软件版本号
    private String softversion;
    //软件地址ID，sys_file.id
    private Integer fileid;
    //是否为必须更新 0：不是必须更新 1：必须更新
    private Integer ismust;
    //版本更新说明
    private String remark;
    //更新发布时间
    private Date ct;
    //版本状态 0：可供升级 1：禁用升级
    private Integer dm;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：软件类型 根据软件类型进行更新 如内部端 公众端
     */
    public void setSoftid(Integer softid) {
        this.softid = softid;
    }
    /**
     * 获取：软件类型 根据软件类型进行更新 如内部端 公众端
     */
    public Integer getSoftid() {
        return softid;
    }
    /**
     * 设置：组织机构ID 可以用来根据组织机构的不同进行分别更新
     */
    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }
    /**
     * 获取：组织机构ID 可以用来根据组织机构的不同进行分别更新
     */
    public Integer getOrgid() {
        return orgid;
    }
    /**
     * 设置：软件版本号
     */
    public void setSoftversion(String softversion) {
        this.softversion = softversion;
    }
    /**
     * 获取：软件版本号
     */
    public String getSoftversion() {
        return softversion;
    }
    /**
     * 设置：软件地址ID，sys_file.id
     */
    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }
    /**
     * 获取：软件地址ID，sys_file.id
     */
    public Integer getFileid() {
        return fileid;
    }
    /**
     * 设置：是否为必须更新 0：不是必须更新 1：必须更新
     */
    public void setIsmust(Integer ismust) {
        this.ismust = ismust;
    }
    /**
     * 获取：是否为必须更新 0：不是必须更新 1：必须更新
     */
    public Integer getIsmust() {
        return ismust;
    }
    /**
     * 设置：版本更新说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：版本更新说明
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置：更新发布时间
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }
    /**
     * 获取：更新发布时间
     */
    public Date getCt() {
        return ct;
    }
    /**
     * 设置：版本状态 0：可供升级 1：禁用升级
     */
    public void setDm(Integer dm) {
        this.dm = dm;
    }
    /**
     * 获取：版本状态 0：可供升级 1：禁用升级
     */
    public Integer getDm() {
        return dm;
    }

}
