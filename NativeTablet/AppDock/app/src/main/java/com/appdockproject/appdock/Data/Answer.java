package com.appdockproject.appdock.Data;

/**
 * Created by andrew on 9/23/16.
 */
public class Answer {


    private String age;
    private String profession;
    private String question;
    private String time;

    public Answer(){

    }

//Setters
    public void setAge(String age) {
        this.age = age;
    }

    public void setProfession(String profession){
        this.profession = profession;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTime(String time){
        this.time = time;
    }


//Getters
    public String getAge() {
        return age;
    }

    public String getProfession(){
        return profession;
    }

    public String getQuestion() {return question;}

    public String getTime(){
        return time;
    }
}
