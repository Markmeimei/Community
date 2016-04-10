package com.example.community.bean;

/**
 * Created by 林炜智 on 2016/4/10.
 * 常住境外人员登记表    随行家属情况类
 */
public class Overseas_Dependent {
    private String relation;    //关系
    private String name;    //姓名
    private String sex; //性别
    private String identityNumber;  //身份证号/或护照号
    private String address; //在华住址

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
