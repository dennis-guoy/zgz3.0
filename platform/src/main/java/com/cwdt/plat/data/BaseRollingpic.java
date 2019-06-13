package com.cwdt.plat.data;

import com.cwdt.plat.dataopt.BaseSerializableData;

import java.util.Date;

public class BaseRollingpic extends BaseSerializableData {

    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //软件类型 根据软件类型进行存储 如内部端 公众端
    private Integer softid;
    //组织机构ID 可以用来根据组织机构的不同进行分类，0为全局使用
    private Integer orgid;
    //图片地址ID，sys_file.id
    private Integer fileid;
    //信息导航类型 0：直接HTML显示  1：重定向页面
    private Integer navtype;
    //导航类型为1时需要跳转的链接地址
    private String navurl;
    //图片标题
    private String navtitle;
    //导航类型为0时需要显示的HTML内容
    private String navcontent;
    //滚动图片链接地址
    private String picurl;
    //发布时间
    private Date ct;
    //删除状态：0:未删除 1:已删除
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
     * 设置：软件类型 根据软件类型进行存储 如内部端 公众端
     */
    public void setSoftid(Integer softid) {
        this.softid = softid;
    }
    /**
     * 获取：软件类型 根据软件类型进行存储 如内部端 公众端
     */
    public Integer getSoftid() {
        return softid;
    }
    /**
     * 设置：组织机构ID 可以用来根据组织机构的不同进行分类，0为全局使用
     */
    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }
    /**
     * 获取：组织机构ID 可以用来根据组织机构的不同进行分类，0为全局使用
     */
    public Integer getOrgid() {
        return orgid;
    }
    /**
     * 设置：图片地址ID，sys_file.id
     */
    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }
    /**
     * 获取：图片地址ID，sys_file.id
     */
    public Integer getFileid() {
        return fileid;
    }
    /**
     * 设置：信息导航类型 0：直接HTML显示  1：重定向页面
     */
    public void setNavtype(Integer navtype) {
        this.navtype = navtype;
    }
    /**
     * 获取：信息导航类型 0：直接HTML显示  1：重定向页面
     */
    public Integer getNavtype() {
        return navtype;
    }
    /**
     * 设置：导航类型为1时需要跳转的链接地址
     */
    public void setNavurl(String navurl) {
        this.navurl = navurl;
    }
    /**
     * 获取：导航类型为1时需要跳转的链接地址
     */
    public String getNavurl() {
        return navurl;
    }
    /**
     * 设置：图片标题
     */
    public void setNavtitle(String navtitle) {
        this.navtitle = navtitle;
    }
    /**
     * 获取：图片标题
     */
    public String getNavtitle() {
        return navtitle;
    }
    /**
     * 设置：导航类型为0时需要显示的HTML内容
     */
    public void setNavcontent(String navcontent) {
        this.navcontent = navcontent;
    }
    /**
     * 获取：导航类型为0时需要显示的HTML内容
     */
    public String getNavcontent() {
        return navcontent;
    }
    /**
     * 设置：发布时间
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }
    /**
     * 获取：发布时间
     */
    public Date getCt() {
        return ct;
    }
    /**
     * 设置：删除状态：0:未删除 1:已删除
     */
    public void setDm(Integer dm) {
        this.dm = dm;
    }
    /**
     * 获取：删除状态：0:未删除 1:已删除
     */
    public Integer getDm() {
        return dm;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
