package com.example.ecommerce.model;

public class User {

    public int uid;
    public String uname;
    public String uaddr;
    public String ucity;
    public String ucontact;
    public String uemail;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUaddr() {
        return uaddr;
    }

    public void setUaddr(String uaddr) {
        this.uaddr = uaddr;
    }

    public String getUcity() {
        return ucity;
    }

    public void setUcity(String ucity) {
        this.ucity = ucity;
    }

    public String getUcontact() {
        return ucontact;
    }

    public void setUcontact(String ucontact) {
        this.ucontact = ucontact;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public User(int uid, String uname, String uaddr, String ucity, String ucontact, String uemail) {
        this.uid = uid;
        this.uname = uname;
        this.uaddr = uaddr;
        this.ucity = ucity;
        this.ucontact = ucontact;
        this.uemail = uemail;
    }
}
