package com.behincom.behincome.Datas;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import static com.behincom.behincome.SQL.RSType.*;

public class DataTest extends com.behincom.behincome.SQL.RSql {

    @com.behincom.behincome.SQL.RAnnot(Key = PRIMARY)
    private int id;
    private String nameq;
    private boolean name1;
    private Boolean name2;
    private Integer name3;
    private Double name4;
    private double name5;
    private Float name6;
    private float name7;
    private long name8;
    private Long name9;
    private Date name10;
    private Time name11;
    private DataTest Objecte;
    private List<DataTest> LObjecte;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return nameq;
    }
    public void setName(String name) {
        this.nameq = name;
    }
    public boolean isName1() {
        return name1;
    }
    public void setName1(boolean name1) {
        this.name1 = name1;
    }
    public Boolean getName2() {
        return name2;
    }
    public void setName2(Boolean name2) {
        this.name2 = name2;
    }
    public Integer getName3() {
        return name3;
    }
    public void setName3(Integer name3) {
        this.name3 = name3;
    }
    public Double getName4() {
        return name4;
    }
    public void setName4(Double name4) {
        this.name4 = name4;
    }
    public double getName5() {
        return name5;
    }
    public void setName5(double name5) {
        this.name5 = name5;
    }
    public Float getName6() {
        return name6;
    }
    public void setName6(Float name6) {
        this.name6 = name6;
    }
    public float getName7() {
        return name7;
    }
    public void setName7(float name7) {
        this.name7 = name7;
    }
    public long getName8() {
        return name8;
    }
    public void setName8(long name8) {
        this.name8 = name8;
    }
    public Long getName9() {
        return name9;
    }
    public void setName9(Long name9) {
        this.name9 = name9;
    }
    public Date getName10() {
        return name10;
    }
    public void setName10(Date name10) {
        this.name10 = name10;
    }
    public Time getName11() {
        return name11;
    }
    public void setName11(Time name11) {
        this.name11 = name11;
    }
    public DataTest getObjecte() {
        return Objecte;
    }
    public void setObjecte(DataTest objecte) {
        Objecte = objecte;
    }
    public List<DataTest> getLObjecte() {
        return LObjecte;
    }
    public void setLObjecte(List<DataTest> LObjecte) {
        this.LObjecte = LObjecte;
    }

    public DataTest() {
        super();
    }
}
