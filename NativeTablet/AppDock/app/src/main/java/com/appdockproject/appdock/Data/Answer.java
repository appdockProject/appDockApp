package com.appdockproject.appdock.Data;

/**
 * Created by andrew on 9/23/16.
 */
public class Answer {


    private String age;
    private String gender;
    private String edu;
    private String profession;
    private String phone;
    private String time;
    private String rating;
    private String use;

    public Answer(){

    }

//Setters
    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {this.gender = gender; }

    public void setEdu(String edu) {this.edu = edu; }

    public void setProfession(String profession){
        this.profession = profession;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setRating(String rating) {this.rating = rating; }

    public void setUse(String use) {this.use = use; }


//Getters
    public String getAge() {
        return age;
    }

    public String getGender(){
        return gender;
    }

    public String getEdu(){
        return edu;
    }

    public String getProfession(){
        return profession;
    }

    public String getPhone() {return phone;}

    public String getTime(){
        return time;
    }

    public String getRating(){
        return rating;
    }

    public String getUse(){
        return use;
    }
}
