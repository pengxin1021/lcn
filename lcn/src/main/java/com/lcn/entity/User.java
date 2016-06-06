package com.lcn.entity;

import java.util.Date;

public class User extends BaseEntity{
	
	private static final long serialVersionUID = 466601073027598417L;

	private Long userId;

    private String userName;

    private String userHomeUrl;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private boolean deleted;
    
    public User(){
    	super();
    	deleted = false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserHomeUrl() {
        return userHomeUrl;
    }

    public void setUserHomeUrl(String userHomeUrl) {
        this.userHomeUrl = userHomeUrl == null ? null : userHomeUrl.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

	@Override
	public Object getPrimaryKey() {
		return userId;
	}
}