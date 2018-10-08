package com.behincom.behincome.Activityes.Setting;

import java.util.ArrayList;
import java.util.List;

public class Initializer<T> {
    public Initializer(List<T> MainItems,
                       List<T> SubItems,
                       Class SubClass,
                       String MainFieldIdName,
                       String SubFieldIdName,
                       String MainFieldTitleName,
                       String SubFieldTitleName,
                       String SubFieldParentIdName,
                       String FragTitle) {
        mMainItems = MainItems;
        mSubItems = SubItems;
        mSubClass = SubClass;
        mMainFieldIdName = MainFieldIdName;
        mSubFieldIdName = SubFieldIdName;
        mMainFieldTitleName = MainFieldTitleName;
        mSubFieldTitleName = SubFieldTitleName;
        mSubFieldParentIdName = SubFieldParentIdName;
        mSubFieldParentIdName = SubFieldParentIdName;
        mFragTitle = FragTitle;
    }

    private List<T> mMainItems = new ArrayList<>();
    private List<T> mSubItems = new ArrayList<>();
    private Class mSubClass = null;
    private String mFragTitle = "";
    private String mMainFieldIdName = "";
    private String mSubFieldIdName = "";
    private String mMainFieldTitleName = "";
    private String mSubFieldTitleName = "";
    private String mSubFieldParentIdName = "";

    public List<T> MainItems() {
        return mMainItems;
    }
    public void MainItems(List<T> mainItems) {
        mMainItems = mainItems;
    }

    public List<T> SubItems() {
        return mSubItems;
    }
    public void SubItems(List<T> subItems) {
        mSubItems = subItems;
    }

    public Class SubClass() {
        return mSubClass;
    }
    public void SubClass(Class subClassName) {
        mSubClass = subClassName;
    }

    public String MainFieldIdName() {
        return mMainFieldIdName;
    }
    public void MainFieldIdName(String mainFieldIdName) {
        mMainFieldIdName = mainFieldIdName;
    }

    public String FragTitle() {
        return mFragTitle;
    }
    public void FragTitle(String FragTitle) {
        this.mFragTitle = FragTitle;
    }

    public String SubFieldIdName() {
        return mSubFieldIdName;
    }
    public void SubFieldIdName(String subFieldIdName) {
        mSubFieldIdName = subFieldIdName;
    }

    public String MainFieldTitleName() {
        return mMainFieldTitleName;
    }
    public void MainFieldTitleName(String mainFieldTitleName) {
        mMainFieldTitleName = mainFieldTitleName;
    }

    public String SubFieldTitleName() {
        return mSubFieldTitleName;
    }
    public void SubFieldTitleName(String subFieldTitleName) {
        mSubFieldTitleName = subFieldTitleName;
    }

    public String SubFieldParentIdName() {
        return mSubFieldParentIdName;
    }
    public void SubFieldParentIdName(String subFieldParentIdName) {
        mSubFieldParentIdName = subFieldParentIdName;
    }
}
