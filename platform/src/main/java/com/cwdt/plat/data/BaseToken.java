package com.cwdt.plat.data;

import com.cwdt.plat.dataopt.BaseSerializableData;

import java.util.Date;

public class BaseToken extends BaseSerializableData {

    private Long userId;
    private String authtoken;
    private Date expiretime;

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the authtoken
     */
    public String getAuthtoken() {
        return authtoken;
    }

    /**
     * @param authtoken the authtoken to set
     */
    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    /**
     * @return the expiretime
     */
    public Date getExpiretime() {
        return expiretime;
    }

    /**
     * @param expiretime the expiretime to set
     */
    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }
}
