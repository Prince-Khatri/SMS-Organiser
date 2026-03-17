package com.smsorganiser.model;

public class CategoryCount {
    public String categoryName;
    int noOfSMS;

    public CategoryCount(String categoryName, int noOfSMS){
        this.categoryName = categoryName;
        this.noOfSMS = noOfSMS;
    }
    public String getCategoryName(){
        return categoryName;
    }
    public int getNoOfSMS(){
        return noOfSMS;
    }
}
