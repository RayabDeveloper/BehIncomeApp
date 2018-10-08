package com.behincom.behincome.Datas.Activityes;

public class TaskConditions {

    private String TaskCondition = "";

    public TaskConditions(int value){
        switch (value){
            case 1:
                TaskCondition = "ورود";
                break;
            case 2:
                TaskCondition = "خروج";
                break;
            case 3:
                TaskCondition = "وظیفه ثبت شده";
                break;
            case 4:
                TaskCondition = "تکمیل شده";
                break;
            case 5:
                TaskCondition = "کنسل شده";
                break;
            case 6:
                TaskCondition = "تایید شده";
                break;
        }
    }

    public String getCondition(){
        return TaskCondition;
    }

}
