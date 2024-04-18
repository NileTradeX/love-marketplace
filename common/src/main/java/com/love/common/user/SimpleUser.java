package com.love.common.user;

public class SimpleUser implements IUser {
    protected Long id;
    protected String uid;
    protected String name;
    protected Integer type;
    private Long roleId;

    public SimpleUser() {

    }

    public SimpleUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleUser(Long id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public SimpleUser(Long id, String name, Integer type, Long roleId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.roleId = roleId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
