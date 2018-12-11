package com.bonc.token.domain;

import java.io.Serializable;

/**
 * @author lidefu
 * @date 2018/12/11 11:22
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 登陆账号
     */
    private String loginId;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 角色编码
     */
    private String roleCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString(){
        return "{" +
                "id=" + this.id +
                ",loginId=" + this.loginId +
                ",password=" + this.password +
                ",userName=" + this.userName +
                ",roleCode=" + this.roleCode +
                "}";
    }

}
