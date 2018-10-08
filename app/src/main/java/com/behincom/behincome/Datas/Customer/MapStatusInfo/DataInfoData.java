package com.behincom.behincome.Datas.Customer.MapStatusInfo;

import com.behincom.behincome.Datas.Customer.Customers;

public class DataInfoData {

    private Customers lCustomer;
    private int customerId, position;
    private String name, activityField;

    public Customers lCustomer() {
        return lCustomer;
    }
    public void lCustomer(Customers lCustomer) {
        this.lCustomer = lCustomer;
    }

    public int position() {
        return position;
    }
    public void position(int position) {
        this.position = position;
    }

    public int customerId() {
        return customerId;
    }
    public void customerId(int customerId) {
        this.customerId = customerId;
    }

    public String name() {
        return name;
    }
    public void name(String name) {
        this.name = name;
    }

    public String activityField() {
        return activityField;
    }
    public void activityField(String activityField) {
        this.activityField = activityField;
    }
}
