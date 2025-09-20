package com.example.labpro;

public class UserHabit {
    private int userHabitID;
    private int userId;
    private int commonHabitID;
    private int customHabitID;
    private String question;
    private int frequency;

    public UserHabit(int userHabitID, int userId, int commonHabitID, int customHabitID, String question, int frequency) {
        this.userHabitID = userHabitID;
        this.userId = userId;
        this.commonHabitID = commonHabitID;
        this.customHabitID = customHabitID;
        this.question = question;
        this.frequency = frequency;
    }

    public int getUserHabitID() {
        return userHabitID;
    }

    public void setUserHabitID(int userHabitID) {
        this.userHabitID = userHabitID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommonHabitID() {
        return commonHabitID;
    }

    public void setCommonHabitID(int commonHabitID) {
        this.commonHabitID = commonHabitID;
    }

    public int getCustomHabitID() {
        return customHabitID;
    }

    public void setCustomHabitID(int customHabitID) {
        this.customHabitID = customHabitID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
