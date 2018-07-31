package com.yxb.cms.model;

import java.util.List;

//this is the return Object of getLogin
public class UserRole {
	Long id ;
    String name ;
    List<DpRole> role ;
    public UserRole (Long id,String username,List<DpRole> roles){
    	this.id = id;
        name = username;
        role = roles;
    }

    public void setId(Long id) {
    	this.id = id;
    }
    public Long getId() {
    	return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setRole(List<DpRole> roles){
        this.role = roles;
    }
    public List<DpRole> getRole(){
        return this.role;
    }
}