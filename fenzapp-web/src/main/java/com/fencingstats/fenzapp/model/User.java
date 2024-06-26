package com.fencingstats.fenzapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "online_user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userid;

    private String name;

    @Column(nullable = false, length=64)
    private String password;

    @Column(name= "first_name", nullable = false, length = 20)
    private String fname;

    @Column(name="last_name", nullable= false, length=20)
    private String lname;

    @Column(nullable = false, unique = true, length= 45)
    private String email;

    @Column(nullable = true, unique = false, length=15)
    private String phoneNum;

    @Column(nullable = true, unique = false, length=150)
    private String address;

    @Column(nullable = true, unique = false, length=9)
    private int SIN;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(Integer id) {
        this.userid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }
}
