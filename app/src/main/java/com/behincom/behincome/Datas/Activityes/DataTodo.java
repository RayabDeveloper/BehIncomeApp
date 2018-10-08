package com.behincom.behincome.Datas.Activityes;

public class DataTodo {

    private int id, sub_act_id;
    private String title, todo_time, todo_date, duration;

    public String duration() {
        return duration;
    }
    public void duration(String duration) {
        this.duration = duration;
    }

    public int id() {
        return id;
    }
    public void id(int id) {
        this.id = id;
    }

    public int sub_act_id() {
        return sub_act_id;
    }
    public void sub_act_id(int sub_act_id) {
        this.sub_act_id = sub_act_id;
    }

    public String title() {
        return title;
    }
    public void title(String title) {
        this.title = title;
    }

    public String todo_time() {
        return todo_time;
    }
    public void todo_time(String todo_time) {
        this.todo_time = todo_time;
    }

    public String todo_date() {
        return todo_date;
    }
    public void todo_date(String todo_date) {
        this.todo_date = todo_date;
    }

}
