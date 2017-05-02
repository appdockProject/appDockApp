package com.appdockproject.appdock.Data;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by jangerhard on 22-Mar-17.
 */

public class App {

    private String desc;
    private String dev1;
    private String devInfo;
    private String devName1;
    private String devName2;
    private String devName3;
    private String keywords;
    private String link;
    private String logo;
    private String name;
    private String smslink;

    public String getDevInfo() {
        return devInfo;
    }

    public void setDevInfo(String devInfo) {
        this.devInfo = devInfo;
    }

    public String getDevName1() {
        return devName1;
    }

    public void setDevName1(String devName1) {
        this.devName1 = devName1;
    }

    public String getDevName2() {
        return devName2;
    }

    public void setDevName2(String devName2) {
        this.devName2 = devName2;
    }

    public String getDevName3() {
        return devName3;
    }

    public void setDevName3(String devName3) {
        this.devName3 = devName3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDev1() {
        return dev1;
    }

    public void setDev1(String dev1) {
        this.dev1 = dev1;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSmslink() {
        return smslink;
    }

    public void setSmslink(String smslink) {
        this.smslink = smslink;
    }

    public App(){

    }

}
