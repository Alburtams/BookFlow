package com.hust.bookflow.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/21 0021.
 */

public class UserBean implements Serializable {
    /**
     * name : 张三
     * stuID : M201873304
     * pwd : 123456
     * email : 1503386385@qq.com
     */

    private String name;
    private String stuID;
    private String pwd;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
