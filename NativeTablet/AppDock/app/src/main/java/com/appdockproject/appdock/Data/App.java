package com.appdockproject.appdock.Data;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by jangerhard on 22-Mar-17.
 */

public class App {

    String name,
            desc,
            dev1,
            dev2,
            keywords,
            link,
            logo,
            smslink;

    DatabaseReference ref;

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

    public String getDev2() {
        return dev2;
    }

    public void setDev2(String dev2) {
        this.dev2 = dev2;
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
